package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TextGraphicsConverterImpl implements TextGraphicsConverter {
    double maxRatio;
    TextColorSchema textColorSchema = new TextColorSchemaImpl();
    int maxWidth;
    int maxHeight;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        StringBuilder str = new StringBuilder("");

        try {
            URL imageUrl = new URL(url);
            InputStream in = imageUrl.openStream();
            BufferedImage img = ImageIO.read(in);
            double ratio = (double) img.getWidth() / img.getHeight();
            System.out.println(ratio);
            int Width = img.getWidth();
            int Height = img.getHeight();
            int newWidth;
            int newHeight;


            if ((this.maxRatio > 0.0 && ratio > this.maxRatio)) {
                throw new BadImageSizeException(ratio, this.maxRatio);
            } else {
                //Проверить ширину и высоту
                if ((this.maxWidth > 0 && this.maxWidth < Width)
                        || (this.maxHeight > 0 && this.maxHeight < Height)) {
                    if ((this.maxHeight == 0) || (this.maxWidth > 0) && (Width / this.maxWidth > Height / this.maxHeight)) {
                        newWidth = this.maxWidth;
                        newHeight = (int) (newWidth * ((double) Height / (double) Width));
                    } else {
                        newHeight = this.maxHeight;
                        newWidth = (int) (newHeight * ((double) Width / (double) Height));
                    }
                } else {
                    newHeight = Height;
                    newWidth = Width;
                }
                Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
                BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
                Graphics2D graphics = bwImg.createGraphics();
                graphics.drawImage(scaledImage, 0, 0, null);
                WritableRaster bwRaster = bwImg.getRaster();

                int[][] result = new int[newHeight][newWidth];

                for (int row = 0; row < newHeight; row++) {
                    str.append("\n");
                    for (int col = 0; col < newWidth; col++) {
                        str.append(this.textColorSchema.convert(bwRaster.getPixel(col, row, new int[3])[0]));
                        str.append(this.textColorSchema.convert(bwRaster.getPixel(col, row, new int[3])[0]));
                    }
                }
                in.close();
            }
        } catch (IOException ioe) {
            //log the error
        }

        return str.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.textColorSchema = schema;
    }

}
