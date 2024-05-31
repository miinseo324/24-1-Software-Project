package hangmanGame;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class hangman {
    private static final int MAX_WRONG_GUESSES = 5;
    private static final int MAX_CORRECT_ANSWERS = 10;
    private static final int MAX_FAILED_ATTEMPTS = 3;

    public static void main(String[] args) throws IOException {
        List<String> words = Files.readAllLines(Paths.get("words.txt"));
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int correctAnswers = 0;
        int failedAttempts = 0;

        while (correctAnswers < MAX_CORRECT_ANSWERS && failedAttempts < MAX_FAILED_ATTEMPTS) {
            String word = words.get(random.nextInt(words.size()));
            String hiddenWord = hideCharacters(word, random);

            System.out.println("Guess the word: " + hiddenWord);

            int wrongGuesses = 0;
            boolean wordGuessed = false;

            while (wrongGuesses < MAX_WRONG_GUESSES && !wordGuessed) {
                System.out.print("Your guess: ");
                String guess = scanner.nextLine();

                if (guess.equalsIgnoreCase(word)) {
                    System.out.println("Correct!");
                    correctAnswers++;
                    wordGuessed = true;
                } else {
                    wrongGuesses++;
                    System.out.println("Wrong! Attempts left: " + (MAX_WRONG_GUESSES - wrongGuesses));
                }
            }

            if (!wordGuessed) {
                System.out.println("The correct word was: " + word);
                failedAttempts++;
            }
        }

        if (correctAnswers >= MAX_CORRECT_ANSWERS) {
            System.out.println("Congratulations! You've won the game.");
        } else {
            System.out.println("Game over. Better luck next time!");
        }

        scanner.close();
    }

    private static String hideCharacters(String word, Random random) {
        char[] chars = word.toCharArray();
        int numToHide = (int) Math.ceil(chars.length * 0.3);

        for (int i = 0; i < numToHide; i++) {
            int index = random.nextInt(chars.length);
            chars[index] = '-';
        }

        return new String(chars);
    }
}
