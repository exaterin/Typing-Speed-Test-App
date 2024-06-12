package app;

import javax.swing.*;

public class StatisticsDisplayManager {
    private JFrame frame;
    private ResultsRecorder resultsRecorder;

    public StatisticsDisplayManager(JFrame frame, ResultsRecorder resultsRecorder) {
        this.frame = frame;
        this.resultsRecorder = resultsRecorder;
    }

    public void showStatistics() {
        java.util.List<String> results = resultsRecorder.readResults();
        JTextArea statsArea = new JTextArea(String.join("\n", results));
        statsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statsArea);
        JOptionPane.showMessageDialog(frame, scrollPane, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
}
