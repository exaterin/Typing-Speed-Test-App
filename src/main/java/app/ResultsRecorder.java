package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ResultsRecorder {
    private final Path resultsPath;

    public ResultsRecorder(String filename) {
        resultsPath = Path.of(filename);
        try {
            if (Files.notExists(resultsPath)) {
                Files.createFile(resultsPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write results into the recorder file
    public void recordResult(String[] enteredWords, int wpm, double accuracyWords, double accuracyLetters, int duration, String selectedLanguage) {

        String result = "Correct words typed: " + enteredWords.length + ", WPM: " + wpm +
                ", Accuracy over words: " + String.format("%.2f%%", accuracyWords) +
                ", Accuracy over letters: " + String.format("%.2f%%", accuracyLetters) +
                ", Duration: " + duration +
                ", Language: " + selectedLanguage;

        try {
            Files.writeString(resultsPath, result + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read the results from the recorder file
    public List<String> readResults() {
        try {
            return Files.readAllLines(resultsPath);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Erase the results from the recorder file
    public void eraseResults() {
        try {
            // Erase the content of the file
            Files.newBufferedWriter(resultsPath, StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}