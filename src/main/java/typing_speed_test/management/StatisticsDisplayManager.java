package typing_speed_test.management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import typing_speed_test.data.ResultsRecorder;
import typing_speed_test.data.StatisticsData;

/**
 * Class to  the display of statistical data from typing tests.
 */
public class StatisticsDisplayManager {
    private final JFrame frame;
    private final ResultsRecorder resultsRecorder;

    /**
     * Constructs a new StatisticsDisplayManager with a given main application frame and instance of ResultsRecorder.
     * @param frame the main application window
     * @param resultsRecorder the results recorder handling the statistical data
     */
    public StatisticsDisplayManager(JFrame frame, ResultsRecorder resultsRecorder) {
        this.frame = frame;
        this.resultsRecorder = resultsRecorder;
    }

    /**
     * Parses results from the data recorder and converts them into a list of StatisticsData objects.
     * @return a list of StatisticsData objects
     */
    private List<StatisticsData> parseResults() {
        List<String> results = resultsRecorder.readResults();
        return results.stream()
                .map(StatisticsData::fromString)
                .collect(Collectors.toList());
    }

    /**
     * Displays statistics in a new dialog with tabs for different charts.
     */
    public void displayStatistics() {
        JDialog statisticsDialog = new JDialog(frame, "Statistics", false);
        statisticsDialog.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("WPM Chart", createWPMChartPanel());
        tabbedPane.add("Accuracy Chart", createAccuracyChartPanel());
        tabbedPane.add("Duration Distribution", createDurationPieChartPanel());

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

    /**
     * Creates a chart panel displaying WPM (Words Per Minute) statistics over time.
     * @return a ChartPanel containing the WPM chart
     */
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

    /**
     * Creates a chart panel displaying accuracy statistics over time.
     * @return a ChartPanel containing the accuracy chart
     */
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

    /**
     * Creates a pie chart panel displaying the distribution of test durations.
     * @return a ChartPanel containing the pie chart
     */
    private ChartPanel createDurationPieChartPanel() {
        List<StatisticsData> statisticsData = parseResults();
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        // Count frequencies of each duration
        Map<Integer, Integer> durationCounts = new HashMap<>();
        for (StatisticsData data : statisticsData) {
            durationCounts.merge(data.getDuration(), 1, Integer::sum);
        }

        // Add data to the dataset
        for (Map.Entry<Integer, Integer> entry : durationCounts.entrySet()) {
            dataset.setValue(entry.getKey() + "s", entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Test Duration Distribution",
                dataset,
                true, true, false);

        return new ChartPanel(chart);
    }

    /**
     * Erases all recorded statistics.
     * @param event the action event triggering this method
     */
    private void eraseStatistics(ActionEvent event) {
        resultsRecorder.eraseResults();

        JOptionPane.showMessageDialog(frame, "Statistics have been erased.", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
