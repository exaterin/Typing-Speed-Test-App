package app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
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

        String content = Files.readString(filePath);
        this.words = List.of(content.split("\\s+"));
    }

    public String getNext() {
        if (currentIndex >= words.size()) {
            return null;
        }
        int endIndex = Math.min(currentIndex + 2, words.size());
        List<String> wordSublist = new ArrayList<>(words.subList(currentIndex, endIndex));
        currentIndex = endIndex;


        return String.join(" ", wordSublist);
    }

    public String getText(){
        return String.join(" ", words);

    }
}
