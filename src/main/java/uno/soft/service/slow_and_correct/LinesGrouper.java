package uno.soft.service.slow_and_correct;

import uno.soft.util.ProgressTracker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinesGrouper {

    /**
     * Main method that takes a list of lines and returns groups of lines.
     *
     * @param lines List of lines to be grouped.
     * @return List of grouped lines, where each group is represented as a Set of lines.
     */
    public List<Set<String>> groupLines(List<String> lines) {
        Set<String> processedLines = new HashSet<>(); // store the lines that were already processed
        List<Set<String>> groups = new ArrayList<>();  // List of sets, each representing a group
        int totalLines = lines.size();

        // Create and start the progress tracker
        ProgressTracker progressTracker = new ProgressTracker(totalLines);
        Thread progressThread = new Thread(progressTracker);
        progressThread.start();

        // Process each line
        for (String line : lines) {
            if(processedLines.contains(line)) {
                continue;
            }
            String[] values = splitLine(line);  // Split line into values
            boolean addedToGroup = false;

            // Check if the line belongs to an existing group
            for (Set<String> group : groups) {
                if (belongsToGroup(values, group)) {
                    processedLines.add(line);
                    group.add(line);  // Add line to the group
                    addedToGroup = true;
                    break;
                }
            }

            // If not added to any group, create a new group
            if (!addedToGroup) {
                processedLines.add(line);
                Set<String> newGroup = new HashSet<>();
                newGroup.add(line);
                groups.add(newGroup);
            }

            // Update progress
            progressTracker.incrementProcessedLines();
        }

        // Stop the progress tracker
        progressTracker.stop();
        try {
            progressThread.join(); // Wait for the progress thread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return groups;  // Return all groups
    }

    /**
     * Splits a line by the semicolon delimiter and trims any surrounding quotes.
     *
     * @param line Line to be split.
     * @return Array of values from the line.
     */
    private String[] splitLine(String line) {
        // Split by semicolon and remove quotes
        String[] values = line.split(";");
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].replace("\"", "").trim();
        }
        return values;
    }

    /**
     * Checks whether a line's values belong to a given group.
     *
     * @param values Array of values from the current line.
     * @param group  Set of lines in the group.
     * @return True if the line shares any value with lines in the group.
     */
    private boolean belongsToGroup(String[] values, Set<String> group) {
        for (String groupLine : group) {
            String[] groupValues = splitLine(groupLine);  // Get values of a line from the group
            if (shareCommonValue(values, groupValues)) {
                return true;  // Belongs to group if any value matches
            }
        }
        return false;
    }

    /**
     * Checks if two lines share at least one value in the same column.
     *
     * @param values1 Values from the first line.
     * @param values2 Values from the second line.
     * @return True if there is a shared value in the same column.
     */
    private boolean shareCommonValue(String[] values1, String[] values2) {
        int len = Math.min(values1.length, values2.length);
        for (int i = 0; i < len; i++) {
            if (!values1[i].isEmpty() && values1[i].equals(values2[i])) {
                return true;  // Return true if there is a matching value
            }
        }
        return false;
    }
}
