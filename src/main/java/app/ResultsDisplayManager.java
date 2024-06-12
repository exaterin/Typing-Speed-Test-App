package app;

import javax.swing.*;

public class ResultsDisplayManager {
    private JFrame frame;
    private ResultsRecorder resultsRecorder;

    public ResultsDisplayManager(JFrame frame, ResultsRecorder resultsRecorder) {
        this.frame = frame;
        this.resultsRecorder = resultsRecorder;
    }

    public void showResults(String textEntered, String originalText, int duration) {
        String[] enteredWords = textEntered.split("\\s+");
        String[] originalWords = originalText.split("\\s+");

        int correctWordsCount = 0;
        int minLength = Math.min(enteredWords.length, originalWords.length);

        for (int i = 0; i < minLength; i++) {
            if (enteredWords[i].equals(originalWords[i])) {
                correctWordsCount++;
            }
        }

        double minutes = duration / 60.0;
        int wpm = (int) (correctWordsCount / minutes);

        resultsRecorder.recordResult("Correct words typed: " + correctWordsCount + ", WPM: " + wpm + ", Duration: " + duration + "s");

        JOptionPane.showMessageDialog(frame,
                "Correct words typed: " + correctWordsCount + "\nWPM: " + wpm,
                "Test Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
