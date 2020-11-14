package lab3.utils;

import java.util.Random;

public class RandomGenerator {
    private static final String EMAIL_HOSTING = "@shitmail.me";
    private static final String LATIN_CHARACTERS_NUMBERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final String CYRILLIC_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщэюя";
    private static final String NUMBERS = "1234567890";

    public static String generateRandomEmail(int nameSize) {
        Random rd = new Random();
        StringBuilder generatedString = new StringBuilder();
        for (int i = 0; i < nameSize; i++) {
            int randomSequence = rd.nextInt(LATIN_CHARACTERS_NUMBERS.length());
            generatedString.append(LATIN_CHARACTERS_NUMBERS.charAt(randomSequence));
        }

        String randomName = generatedString.toString();
        return randomName + EMAIL_HOSTING;
    }

    public static String getRandomCyrillicWord(int length) {
        Random rd = new Random();
        StringBuilder generatedString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomSequence = rd.nextInt(CYRILLIC_CHARACTERS.length());
            generatedString.append(CYRILLIC_CHARACTERS.charAt(randomSequence));
        }

        return generatedString.toString();
    }

    public static String generateRandomPhoneNumber() {
        return "9" + generateRandomNumber(9);
    }

    public static String generateRandomNumber(int length) {
        Random rd = new Random();
        StringBuilder generatedString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomSequence = rd.nextInt(NUMBERS.length());
            generatedString.append(NUMBERS.charAt(randomSequence));
        }

        return generatedString.toString();
    }

    public static int getRandomInt(int ceiling) {
        Random r = new Random();
        return r.nextInt(ceiling);
    }

    public static String getRandomCyrillicLetter() {
        Random r = new Random();
        int randomSequence = r.nextInt(CYRILLIC_CHARACTERS.length());
        return Character.toString(CYRILLIC_CHARACTERS.charAt(randomSequence));
    }
}
