package ru.netology.graphics;

import ru.netology.graphics.image.TextColorSchemaImpl;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverterImpl;
//import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new TextGraphicsConverterImpl(); // Создайте тут объект вашего класса конвертера

//        GServer server = new GSer ver(converter); // Создаём объект сервера
//        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
        String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
        converter.setTextColorSchema(new TextColorSchemaImpl());
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);



    }
}
