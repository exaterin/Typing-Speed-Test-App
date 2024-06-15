package app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class TextLoader {
    private final List<String> words;
    private int currentIndex = 0;

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

    public String getNext() {
        if (currentIndex >= words.size()) {
            currentIndex = 0; // Reset index to loop the words
        }
        int endIndex = Math.min(currentIndex + 20, words.size());
        String result = String.join(" ", words.subList(currentIndex, endIndex));
        currentIndex = endIndex;
        return result;
    }

    public String getText(){
        return String.join(" ", words);

    }
}
