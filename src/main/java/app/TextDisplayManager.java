package app;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TextDisplayManager {
    private final JTextPane textPane;
    private SimpleAttributeSet correctAttr;
    private SimpleAttributeSet incorrectAttr;
    private SimpleAttributeSet untypedAttr;
    private TextLoader textLoader;
    private String currentWords;
    int usedWordsCount;
    int emptySpaceCount;

    public TextDisplayManager(JTextPane textPane) {
        this.usedWordsCount = 0;
        this.emptySpaceCount = 0;
        this.textPane = textPane;
        initAttributes();
    }

    private void initAttributes() {
        correctAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(correctAttr, Color.BLACK);

        incorrectAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(incorrectAttr, Color.RED);

        untypedAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(untypedAttr, Color.GRAY);
    }

    public void updateTextStyles(String textEntered) {

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs, "SansSerif");
        StyleConstants.setFontSize(attrs, 25);

        doc.setCharacterAttributes(0, doc.getLength(), attrs, false);
        int len = textEntered.length() - usedWordsCount - emptySpaceCount;
        String originalText = textPane.getText();

        for (int i = 0; i < len; i++) {
            if (i < originalText.length() && textEntered.charAt(i + usedWordsCount + emptySpaceCount) == originalText.charAt(i)) {
                doc.setCharacterAttributes(i, 1, correctAttr, false);
            } else {
                doc.setCharacterAttributes(i, 1, incorrectAttr, false);
            }
        }

        String text = String.join(" ", currentWords);

        if (len < doc.getLength()) {
            doc.setCharacterAttributes(len, doc.getLength() - len, untypedAttr, false);
        }

        if ( len >= text.length()) {
            usedWordsCount += currentWords.length();
            emptySpaceCount += 1;
            currentWords = textLoader.getNext(); // Load next set of words
            if (currentWords != null) {
                updateTextPane(); // Update pane with new text
            }
        }

    }

    private void updateTextPane() {
        String text = String.join(" ", currentWords);

        try {
            StyledDocument doc = textPane.getStyledDocument();
            doc.remove(0, doc.getLength());

            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attrs, "SansSerif");
            StyleConstants.setFontSize(attrs, 25);
            doc.insertString(0, text, untypedAttr);
            doc.setCharacterAttributes(0, doc.getLength(), attrs, false);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void setText(TextLoader textLoader) throws IOException {

        this.textLoader = textLoader;

        String text = textLoader.getNext();
        currentWords = text;
        StyledDocument doc = textPane.getStyledDocument();

        try {
            // Clear the existing text
            doc.remove(0, doc.getLength());

            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attrs, "SansSerif");
            StyleConstants.setFontSize(attrs, 25);

            // Insert the new text with the intended attributes
            doc.insertString(0, text, attrs);

            doc.setCharacterAttributes(0, doc.getLength(), attrs, false);

        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    void updateCount(){
        usedWordsCount = 0;
        emptySpaceCount = 0;

    }
}
