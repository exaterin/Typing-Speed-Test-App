package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.List;
import java.util.stream.Collectors;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsDisplayManager {
    private final JFrame frame;
    private final ResultsRecorder resultsRecorder;
    private JTextArea statsArea;

    public StatisticsDisplayManager(JFrame frame, ResultsRecorder resultsRecorder) {
        this.frame = frame;
        this.resultsRecorder = resultsRecorder;
    }

    // Parse results from the recorder file
    private List<StatisticsData> parseResults() {
        List<String> results = resultsRecorder.readResults();
        return results.stream()
                .map(StatisticsData::fromString)
                .collect(Collectors.toList());
    }

    public void displayStatistics() {
        JDialog statisticsDialog = new JDialog(frame, "Statistics", false);
        statisticsDialog.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("WPM Chart", createWPMChartPanel());
        tabbedPane.add("Accuracy Chart", createAccuracyChartPanel());

        JPanel buttonPanel = new JPanel();
        JButton eraseButton = new JButton("Erase Statistics");
        eraseButton.addActionListener(this::eraseStatistics);
        buttonPanel.add(eraseButton);

        statisticsDialog.add(tabbedPane, BorderLayout.CENTER);
        statisticsDialog.add(buttonPanel, BorderLayout.SOUTH);

        statisticsDialog.setSize(600, 400);
        statisticsDialog.setLocationRelativeTo(frame);
        statisticsDialog.setVisible(true);
    }

    private ChartPanel createWPMChartPanel() {
        List<StatisticsData> statisticsData = parseResults();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int index = 1;

        if (statisticsData.isEmpty()) {
            System.out.println("No wpm data available.");
            return null;
        }

        for (StatisticsData data : statisticsData) {
            dataset.addValue(data.getWpm(), "WPM", String.valueOf(index));
            index++;
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Words Per Minute (WPM) Over Time",
                "Attempt",
                "WPM",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        return new ChartPanel(chart);
    }

    private ChartPanel createAccuracyChartPanel() {
        List<StatisticsData> statisticsData = parseResults();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int index = 1;

        if (statisticsData.isEmpty()) {
            System.out.println("No accuracy data available.");
            return null;
        }

        for (StatisticsData data : statisticsData) {
            dataset.addValue(data.getAccuracyWords(), "Accuracy Over Words", String.valueOf(index));
            dataset.addValue(data.getAccuracyLetters(), "Accuracy Over Letters", String.valueOf(index));
            index++;
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Accuracy Over Time",
                "Attempt",
                "Accuracy (%)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        return new ChartPanel(chart);
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
