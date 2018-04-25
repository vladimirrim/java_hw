package ru.spbau.egorov.hw_2;


/**
 * This class implements manager for collecting data from one game session.
 */
public class StatisticsManager {
    private StringBuilder statistics = new StringBuilder();

    /**
     * Writes statictics to the log.
     *
     * @param stats is string to write.
     */
    public void writeStatistics(String stats) {
        statistics.append(stats).append('\n');
    }

    public String getStatistics() {
        return statistics.toString();
    }
}
