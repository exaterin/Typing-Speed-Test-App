package app;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TextDisplayManager {
    private final JTextPane textPane;
    private SimpleAttributeSet correctAttr;
    private SimpleAttributeSet incorrectAttr;
    private SimpleAttributeSet untypedAttr;

    public TextDisplayManager(JTextPane textPane) {
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
        int len = textEntered.length();
        String originalText = textPane.getText().substring(0, Math.min(len, doc.getLength()));

        for (int i = 0; i < len; i++) {
            if (i < originalText.length() && textEntered.charAt(i) == originalText.charAt(i)) {
                doc.setCharacterAttributes(i, 1, correctAttr, false);
            } else {
                doc.setCharacterAttributes(i, 1, incorrectAttr, false);
            }
        }

        if (len < doc.getLength()) {
            doc.setCharacterAttributes(len, doc.getLength() - len, untypedAttr, false);
        }
    }

    public void setText(String text) {
        StyledDocument doc = textPane.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
            doc.insertString(0, text, untypedAttr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
