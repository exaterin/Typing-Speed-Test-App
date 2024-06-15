package app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * TextLoader is responsible for loading and randomizing words from text files based on the language.
 * It provides methods to get text in chunks or the entire text.
 */
public class TextLoader {
    private final List<String> words;
    private int currentIndex = 0;

    /**
     * Constructs a TextLoader for the specified language.
     * Loads words from a file corresponding to the given language and shuffles them.
     *
     * @param language the language of the text to load ("English", "Czech", "Russian", "Spanish").
     * @throws IOException if there is an error reading the file or if the language is not supported.
     */
    public TextLoader(String language) throws IOException {
        String basePath = "src/main/resources/texts/";

        Path filePath;
        switch (language.toLowerCase()) {
            case "english" -> filePath = Path.of(basePath + "english.txt");
            case "czech" -> filePath = Path.of(basePath + "czech.txt");
            case "russian" -> filePath = Path.of(basePath + "russian.txt");
            case "spanish" -> filePath = Path.of(basePath + "spanish.txt");
            default -> throw new IOException("Unsupported language");
        }

        // Read all lines into a list
        List<String> allLines = Files.readAllLines(filePath);
        words = new ArrayList<>();

        for (String line : allLines) {
            words.add(line.trim());
        }

        // Shuffle the words to create a random sequence each time
        Collections.shuffle(words);
    }

    /**
     * Retrieves the next chunk of words.
     * If the end of the list is reached, it wraps around to the beginning.
     *
     * @return a string containing the next chunk of words.
     */
    public String getNext() {
        if (currentIndex >= words.size()) {
            currentIndex = 0; // Reset index to loop the words
        }
        int endIndex = Math.min(currentIndex + 20, words.size());
        String result = String.join(" ", words.subList(currentIndex, endIndex));
        currentIndex = endIndex;
        return result;
    }

    /**
     * Returns all words as a single string.
     *
     * @return a string containing all the words loaded, joined by spaces.
     */
    public String getText(){
        return String.join(" ", words);
    }
}
