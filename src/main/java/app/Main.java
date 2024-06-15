package app;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;


public class Main {

    private JFrame frame;
    private JTextPane textPane;
    private TextDisplayManager textDisplayManager;
    private JTextArea inputArea;
    private JLabel timerLabel;
    private JComboBox<String> durationComboBox, languageComboBox;
    private JButton startButton, showStatsButton;
    private Timer timer;
    private int timeLeft;
    private final StatisticsDisplayManager statisticsDisplayManager;
    private final ResultsDisplayManager resultsDisplayManager;




    public Main() {
        // Filename for storing results
        ResultsRecorder resultsRecorder = new ResultsRecorder("src/main/resources/typing_results.txt");

        initializeUI();

        statisticsDisplayManager = new StatisticsDisplayManager(frame, resultsRecorder);
        resultsDisplayManager = new ResultsDisplayManager(frame, resultsRecorder);

    }

    private void initializeUI() {
        frame = new JFrame("Typing Speed Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("SansSerif", Font.BOLD, 10));
        textPane.setPreferredSize(new Dimension(800, 200));

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet centerAttr = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttr, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), centerAttr, true);

        textDisplayManager = new TextDisplayManager(textPane);

        inputArea = new JTextArea();
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                textDisplayManager.updateTextStyles(inputArea.getText().trim());
            }
        });

        timerLabel = new JLabel("Time: -- seconds", JLabel.CENTER);
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        startButton = new JButton("Start");
        startButton.addActionListener(this::startTest);

        showStatsButton = new JButton("Show Statistics");
        showStatsButton.addActionListener(e -> statisticsDisplayManager.displayStatistics());


        String[] durations = {"15", "30", "60", "120"};
        durationComboBox = new JComboBox<>(durations);

        String[] languages = {"English", "Czech", "Russian", "Spanish"};
        languageComboBox = new JComboBox<>(languages);

        JPanel controlPanel = new JPanel();
        controlPanel.add(timerLabel);
        controlPanel.add(durationComboBox);
        controlPanel.add(languageComboBox);
        controlPanel.add(startButton);
        controlPanel.add(showStatsButton);

        frame.add(new JScrollPane(textPane), BorderLayout.NORTH);
        frame.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(frame.getPreferredSize());
        frame.setMaximumSize(frame.getPreferredSize());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startTest(ActionEvent e) {
        String selectedLanguage = (String) languageComboBox.getSelectedItem();
        try {
            assert selectedLanguage != null;
            textDisplayManager.setText(selectedLanguage);
            textDisplayManager.updateCount();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Failed to load file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        inputArea.setEditable(true);
        inputArea.setText("");
        inputArea.requestFocusInWindow();
        int duration = Integer.parseInt((String) Objects.requireNonNull(durationComboBox.getSelectedItem()));
        timeLeft = duration;
        startButton.setEnabled(false);
        durationComboBox.setEnabled(false);
        languageComboBox.setEnabled(false);
        showStatsButton.setEnabled(false);

        timer = new Timer(1000, event -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft + " seconds");
            if (timeLeft <= 0) {
                timer.stop();
                inputArea.setEditable(false);
                startButton.setEnabled(true);
                durationComboBox.setEnabled(true);
                languageComboBox.setEnabled(true);
                showStatsButton.setEnabled(true);

                try {
                    TextLoader textLoader = new TextLoader(selectedLanguage);
                    resultsDisplayManager.showResults(inputArea.getText().trim(), textLoader.getText(), duration, selectedLanguage);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}