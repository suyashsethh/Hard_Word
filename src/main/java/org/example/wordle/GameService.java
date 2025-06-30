package org.example.wordle;

public class GameService {
    private final String answer;
    private final WordRepository wordRepo;

    public GameService(WordRepository wordRepo) {
        this.wordRepo = wordRepo;
        this.answer = wordRepo.getRandomWord();
    }

    public boolean isValidWord(String word) {
        return wordRepo.isValidWord(word);
    }

    public String getAnswer() {
        return answer;
    }

    public String checkGuess(String guess) {
        String[] result = {"⬜", "⬜", "⬜", "⬜"};
        boolean[] matched = new boolean[4];
        char[] answerChars = answer.toCharArray();

        // Green pass
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = "🟩";
                matched[i] = true;
                answerChars[i] = '*';
            }
        }

        // Yellow pass
        for (int i = 0; i < 4; i++) {
            if (result[i].equals("⬜")) {
                for (int j = 0; j < 4; j++) {
                    if (!matched[j] && guess.charAt(i) == answerChars[j]) {
                        result[i] = "🟨";
                        matched[j] = true;
                        answerChars[j] = '*';
                        break;
                    }
                }
            }
        }

        return String.join("", result);
    }
}
