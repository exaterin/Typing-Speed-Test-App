package typing_speed_test.management;

import typing_speed_test.data.TextLoader;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;

/**
 * Displays text for the typing text.
 */
public class TextDisplayManager {
    private final JTextPane textPane;
    private SimpleAttributeSet correctAttr;
    private SimpleAttributeSet incorrectAttr;
    private SimpleAttributeSet untypedAttr;
    private TextLoader textLoader;
    private String currentWords;
    int usedWordsCount;
    int emptySpaceCount;

    /**
     * Constructs a TextDisplayManager with a given JTextPane.
     *
     * @param textPane The JTextPane to manage.
     */
    public TextDisplayManager(JTextPane textPane) {
        this.usedWordsCount = 0;
        this.emptySpaceCount = 0;
        this.textPane = textPane;
        initAttributes();
    }

    /**
     * Initializes text attributes for correct, incorrect, and untyped text.
     */
    private void initAttributes() {
        correctAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(correctAttr, Color.BLACK);

        incorrectAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(incorrectAttr, Color.RED);

        untypedAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(untypedAttr, Color.GRAY);
    }

    /**
     * Updates the text styles based on the input from the user, highlighting correct and incorrect letters with different colors.
     * Red is used for incorrect letters, black for correct ones and grey is to unseen letters.
     *
     * @param textEntered the text entered by the user
     */
    public void updateTextStyles(String textEntered) {

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs, "SansSerif");
        StyleConstants.setFontSize(attrs, 25);

        doc.setCharacterAttributes(0, doc.getLength(), attrs, false);
        int len = textEntered.length() - usedWordsCount - emptySpaceCount;
        String originalText = textPane.getText();

        // Mark incorrect letters with red color, correct ones with black color
        for (int i = 0; i < len; i++) {
            if (i < originalText.length() && textEntered.charAt(i + usedWordsCount + emptySpaceCount) == originalText.charAt(i)) {
                doc.setCharacterAttributes(i, 1, correctAttr, false);
            } else {
                doc.setCharacterAttributes(i, 1, incorrectAttr, false);
            }
        }

        String text = String.join(" ", currentWords);

        // Unseen letters are set to grey
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

    /**
     * Updates the text pane with new text from the text loader.
     */
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

    /**
     * Sets the initial text for the typing test based on the selected language.
     *
     * @param textLoader the loader responsible for fetching the initial text
     * @throws IOException if there is an issue reading the initial text
     */
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

            // Insert the new text with the attributes
            doc.insertString(0, text, attrs);

            doc.setCharacterAttributes(0, doc.getLength(), attrs, false);

        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resets the word and space counters to zero.
     */
    public void updateCount(){
        usedWordsCount = 0;
        emptySpaceCount = 0;
    }
}
