package typing_speed_test.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Manages the recording, reading, and erasing of results from typing tests.
 * This class handles all file operations related to storing and retrieving typing test results.
 */
public class ResultsRecorder {
    private final Path resultsPath;

    /**
     * Constructs a ResultsRecorder.
     *
     * @param filename the name of the file used to store results
     */
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

    /**
     * Records a single result of a typing test into the results file.
     * The result includes various metrics such as words per minute, accuracy, duration, and language.
     *
     * @param enteredWords array of words entered by the user during the test
     * @param wpm words per minute achieved
     * @param accuracyWords accuracy percentage over words typed
     * @param accuracyLetters accuracy percentage over letters typed
     * @param duration duration of the test in seconds
     * @param selectedLanguage the language used during the test
     */
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

    /**
     * Reads and returns all recorded results from the results file.
     *
     * @return a list of strings, each representing a recorded result
     */
    public List<String> readResults() {
        try {
            return Files.readAllLines(resultsPath);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Erases all recorded results by clearing the contents of the results file.
     */
    public void eraseResults() {
        try {
            // Erase the content of the file
            Files.newBufferedWriter(resultsPath, StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}