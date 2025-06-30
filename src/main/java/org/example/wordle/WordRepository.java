package org.example.wordle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordRepository {
    private List<String> words = new ArrayList<>();

    public WordRepository() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("words.txt")))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (line.length() == 4) {
                    words.add(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load word list from words.txt", e);
        }
    }

    public String getRandomWord() {
        return words.get(new Random().nextInt(words.size()));
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }
}
