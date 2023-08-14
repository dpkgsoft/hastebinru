package com.dpkgsoft.ruhaste;

import java.io.File;
import java.util.Random;

public class Utils {
    private static final Random random = new Random();

    public static File getDirectory() {
        return new File(Main.config.getDir());
    }

    public static String generateKey() {
        int length = Main.config.getKeyLength();
        StringBuilder text = new StringBuilder();
        int start = random.nextInt(2);

        for (int i = 0; i < length; i++) {
            text.append(randomSymbol(i % 2 != start));
        }

        return text.toString();
    }

    private static char randomSymbol(boolean vowel) {
        char[] vowels = "aeiou".toCharArray();
        char[] consonants = "bcdfghjklmnpqrstvwxyz".toCharArray();

        if (vowel) {
            return vowels[random.nextInt(vowels.length)];
        } else {
            return consonants[random.nextInt(consonants.length)];
        }
    }
}
