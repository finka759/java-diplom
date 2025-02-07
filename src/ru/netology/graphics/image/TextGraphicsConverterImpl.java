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
    TextColorSchema textColorSchema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        StringBuilder str = new StringBuilder(new String());

        try {
            URL imageUrl = new URL(url);
            InputStream in = imageUrl.openStream();
            BufferedImage sampleImage = ImageIO.read(in);



            int newWidth = sampleImage.getWidth();
            int newHeight = sampleImage.getHeight();


            BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bwImg.createGraphics();
            graphics.drawImage(sampleImage, 0, 0, null);
            WritableRaster bwRaster = bwImg.getRaster();


            int width = sampleImage.getWidth();
            int height = sampleImage.getHeight();
            int[][] result = new int[height][width];

            for (int row = 0; row < height; row++) {
                str.append("\n");
                for (int col = 0; col < width; col++) {

                    str.append( this.textColorSchema.convert(bwRaster.getPixel(col, row, new int[3])[0]));

                }
            }

            in.close();
        } catch (IOException ioe) {
            //log the error
        }



        return str.toString();
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
