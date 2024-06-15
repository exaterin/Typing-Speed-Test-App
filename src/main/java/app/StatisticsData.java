package app;

public class StatisticsData {
    private final double accuracyWords;
    private final double accuracyLetters;
    private final double wpm;
    private final int duration;
    private final String language;

    private StatisticsData(double accuracyWords, double accuracyLetters, double wpm, int duration, String language) {
        this.accuracyWords = accuracyWords;
        this.accuracyLetters = accuracyLetters;
        this.wpm = wpm;
        this.duration = duration;
        this.language = language;
    }

    // Get the data from the results line
    public static StatisticsData fromString(String resultLine) {
        String[] parts = resultLine.split(", ");
        double accuracyWords = Double.parseDouble(parts[2].split(": ")[1].replace("%", ""));
        double accuracyLetters = Double.parseDouble(parts[3].split(": ")[1].replace("%", ""));
        double wpm = Double.parseDouble(parts[1].split(": ")[1]);
        int duration = Integer.parseInt(parts[4].split(": ")[1]);
        String language = parts[5].split(": ")[1];
        return new StatisticsData(accuracyWords, accuracyLetters, wpm, duration, language);
    }

    public double getAccuracyWords() {
        return accuracyWords;
    }

    public double getAccuracyLetters() {
        return accuracyLetters;
    }

    public double getWpm() {
        return wpm;
    }

    public int getDuration() {
        return duration;
    }

    public String getLanguage(){
        return language;
    }

}


