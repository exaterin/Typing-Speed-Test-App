package app;

import javax.swing.*;

public class ResultsDisplayManager {
    private JFrame frame;
    private ResultsRecorder resultsRecorder;

    public ResultsDisplayManager(JFrame frame, ResultsRecorder resultsRecorder) {
        this.frame = frame;
        this.resultsRecorder = resultsRecorder;
    }

    // Show the results in a separate window and store them into recorder
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

    // Accuracy over words calculation
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

    // Accuracy over letters calculation
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
