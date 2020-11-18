package ru.achebykin.helper;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static void saveCSV(MultipartFile file) throws IOException {
        File destFile = new File("file.csv");

        InputStream initialStream = file.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        try (OutputStream outStream = new FileOutputStream(destFile)) {
            outStream.write(buffer);
        }
    }
}
