package app;

/**
 * Represents the statistical data for a typing test.
 * This class encapsulates statistics such as accuracy in words and letters, words per minute, duration of the test and the language used.
 */

public class StatisticsData {
    private final double accuracyWords;
    private final double accuracyLetters;
    private final double wpm;
    private final int duration;
    private final String language;

    /**
     * Constructs a new instance of StatisticsData.
     *
     * @param accuracyWords accuracy percentage of words
     * @param accuracyLetters accuracy percentage of letters
     * @param wpm words per minute achieved in the test
     * @param duration duration of the test in seconds
     * @param language language used in the test
     */
    private StatisticsData(double accuracyWords, double accuracyLetters, double wpm, int duration, String language) {
        this.accuracyWords = accuracyWords;
        this.accuracyLetters = accuracyLetters;
        this.wpm = wpm;
        this.duration = duration;
        this.language = language;
    }

    /**
     * Parses a line of result data into a StatisticsData object.
     *
     * @param resultLine A string containing the test results in a specific format
     * @return A new instance of StatisticsData containing the parsed values
     */
    public static StatisticsData fromString(String resultLine) {
        String[] parts = resultLine.split(", ");
        double accuracyWords = Double.parseDouble(parts[2].split(": ")[1].replace("%", ""));
        double accuracyLetters = Double.parseDouble(parts[3].split(": ")[1].replace("%", ""));
        double wpm = Double.parseDouble(parts[1].split(": ")[1]);
        int duration = Integer.parseInt(parts[4].split(": ")[1]);
        String language = parts[5].split(": ")[1];
        return new StatisticsData(accuracyWords, accuracyLetters, wpm, duration, language);
    }

    /**
     * Returns the accuracy of words.
     *
     * @return the words accuracy as a percentage.
     */
    public double getAccuracyWords() {
        return accuracyWords;
    }

    /**
     * Returns the accuracy of letters.
     *
     * @return the letters accuracy as a percentage.
     */
    public double getAccuracyLetters() {
        return accuracyLetters;
    }

    /**
     * Returns the words per minute.
     *
     * @return the rate of words per minute.
     */
    public double getWpm() {
        return wpm;
    }

    /**
     * Returns the duration of the test.
     *
     * @return the duration in seconds.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the language of the test.
     *
     * @return the language used during the test.
     */
    public String getLanguage(){
        return language;
    }

}


