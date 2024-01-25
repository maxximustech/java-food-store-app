/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package test.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * The Helper class.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class Helper {
    /**
     * Generates a random string based on a given length of characters.
     *
     * @param length the length of characters
     * @return the string
     */
    public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }

    /**
     * Reads a file.
     *
     * @param path the path
     * @return the string
     * @throws IOException the io exception
     */
    public String readFile(String path) throws IOException {
        FileReader fr = new FileReader(path);
        int c;
        StringBuilder s = new StringBuilder();
        while((c = fr.read()) != -1){
            s.append((char) c);
        }
        fr.close();
        return s.toString();
    }
}
