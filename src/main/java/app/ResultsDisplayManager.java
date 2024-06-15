package app;

import javax.swing.*;

/**
 * Manages the displaying of typing test results.
 */

public class ResultsDisplayManager {
    private JFrame frame;
    private ResultsRecorder resultsRecorder;

    /**
     * Constructs a ResultsDisplayManager.
     *
     * @param frame            the main application window
     * @param resultsRecorder  the object responsible for recording results
     */
    public ResultsDisplayManager(JFrame frame, ResultsRecorder resultsRecorder) {
        this.frame = frame;
        this.resultsRecorder = resultsRecorder;
    }

    /**
     * Shows the typing test results in a message dialog and stores them using the results recorder.
     *
     * @param textEntered      the text entered by the user
     * @param originalText     the original text displayed for typing
     * @param duration         the duration of the test in seconds
     * @param selectedLanguage the language used in the test
     */
    public void showResults(String textEntered, String originalText, int duration, String selectedLanguage) {
        String[] enteredWords = textEntered.split("\\s+");
        String[] originalWords = originalText.split("\\s+");

        double accuracyWords = calculateAccuracyOverWords(enteredWords, originalWords);
        double accuracyLetters = calculateAccuracyOverLetters(textEntered, originalText);

        double minutes = duration / 60.0;
        int wpm = (int) (enteredWords.length / minutes);

        // Record the result to a file
        resultsRecorder.recordResult(enteredWords, wpm, accuracyWords, accuracyLetters, duration, selectedLanguage);

        // Display the results
        JOptionPane.showMessageDialog(frame,
                "Words typed: " + enteredWords.length +
                        "\nWPM: " + wpm +
                        "\nAccuracy over words: " + String.format("%.2f%%", accuracyWords) +
                        "\nAccuracy over letters: " + String.format("%.2f%%", accuracyLetters),
                "Test Results", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Calculates the accuracy of typed words against the original text.
     *
     * @param enteredWords  the words entered by the user
     * @param originalWords the original words displayed
     * @return the percentage of correctly typed words
     */
    private double calculateAccuracyOverWords(String[] enteredWords, String[] originalWords) {
        int correctWordsCount = 0;
        int minLength = Math.min(enteredWords.length, originalWords.length);
        for (int i = 0; i < minLength; i++) {
            if (enteredWords[i].equals(originalWords[i])) {
                correctWordsCount++;
            }
        }
        return (double) correctWordsCount / enteredWords.length * 100;
    }

    /**
     * Calculates the accuracy of typed letters against the original text.
     *
     * @param textEntered  the complete string entered by the user
     * @param originalText the original text displayed
     * @return the percentage of correctly typed letters
     */
    private double calculateAccuracyOverLetters(String textEntered, String originalText) {
        int correctLettersCount = 0;
        int minLen = Math.min(textEntered.length(), originalText.length());

        for (int i = 0; i < minLen; i++) {
            if (textEntered.charAt(i) == originalText.charAt(i)) {
                correctLettersCount++;
            }
        }

        int totalLettersEntered = textEntered.length();

        return totalLettersEntered > 0 ? (double) correctLettersCount / totalLettersEntered * 100 : 0.0;
    }
}
