package app;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class TextLoader {
    private final Path filePath;

    public TextLoader(String language) throws IOException {
        String basePath = "src/main/resources/texts/";

        switch (language.toLowerCase()) {
            case "english" -> this.filePath = Path.of(basePath + "english.txt");
            case "spanish" -> this.filePath = Path.of(basePath + "spanish.txt");
            case "czech" -> this.filePath = Path.of(basePath + "czech.txt");
            default -> throw new IOException("Unsupported language");
        }
    }

    public String getText() throws IOException {
        return Files.readString(filePath);
    }
}
