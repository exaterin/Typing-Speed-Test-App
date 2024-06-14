package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StatisticsDisplayManager {
    private final JFrame frame;
    private final ResultsRecorder resultsRecorder;
    private JTextArea statsArea;

    public StatisticsDisplayManager(JFrame frame, ResultsRecorder resultsRecorder) {
        this.frame = frame;
        this.resultsRecorder = resultsRecorder;
    }

    public void showStatistics() {
        statsArea = new JTextArea();
        updateStatsArea();

        JScrollPane scrollPane = new JScrollPane(statsArea);
        JPanel buttonPanel = new JPanel();
        JButton eraseButton = new JButton("Erase Statistics");
        eraseButton.addActionListener(this::eraseStatistics);
        buttonPanel.add(eraseButton);

        JDialog statsDialog = new JDialog(frame, "Statistics", true);
        statsDialog.setLayout(new BorderLayout());
        statsDialog.add(scrollPane, BorderLayout.CENTER);
        statsDialog.add(buttonPanel, BorderLayout.SOUTH);
        statsDialog.setSize(400, 300);
        statsDialog.setLocationRelativeTo(frame);
        statsDialog.setVisible(true);
    }

    // Method to load and display results
    private void updateStatsArea() {
        java.util.List<String> results = resultsRecorder.readResults();
        statsArea.setText(String.join("\n", results));
        statsArea.setEditable(false);
    }

    // Erase the statistics
    private void eraseStatistics(ActionEvent event) {
        resultsRecorder.eraseResults();

        // Refresh the text area to show the updated (empty) results
        updateStatsArea();

        JOptionPane.showMessageDialog(frame, "Statistics have been erased.", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
