package com.bootcamp.nomnom.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class StringGenerator {
    public static String getRandomFilename(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int index = (int) (symbols.length() * Math.random());
            sb.append(symbols.charAt(index));
        }

        String filename = sb.toString() + "." + extension;

        return filename;
    }
}