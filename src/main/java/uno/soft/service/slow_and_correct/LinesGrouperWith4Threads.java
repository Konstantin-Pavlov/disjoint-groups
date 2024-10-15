package uno.soft.service.slow_and_correct;

import uno.soft.util.ProgressTracker;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LinesGrouperWith4Threads {
    /**
     * Main method that takes a list of lines and returns groups of lines.
     *
     * @param lines List of lines to be grouped.
     * @return List of grouped lines, where each group is represented as a Set of lines.
     */
    public List<Set<String>> groupLines(List<String> lines) {
        Set<String> processedLines = ConcurrentHashMap.newKeySet(); // Thread-safe set for processed lines
        List<Set<String>> groups = new CopyOnWriteArrayList<>();  // Thread-safe list for groups
        int totalLines = lines.size();

        // Create and start the progress tracker
        ProgressTracker progressTracker = new ProgressTracker(totalLines);
        Thread progressThread = new Thread(progressTracker);
        progressThread.start();

        // Split the lines into two halves
        List<String> l1 = lines.subList(0, totalLines / 4);
        List<String> l2 = lines.subList(totalLines / 4, totalLines / 2);
        List<String> l3 = lines.subList(totalLines / 2, totalLines / 2 + totalLines / 4);
        List<String> l4 = lines.subList(totalLines / 2 + totalLines / 4, totalLines);

        // Create tasks for processing each half
        Runnable task1 = () -> processLines(l1, processedLines, groups, progressTracker);
        Runnable task2 = () -> processLines(l2, processedLines, groups, progressTracker);
        Runnable task3 = () -> processLines(l3, processedLines, groups, progressTracker);
        Runnable task4 = () -> processLines(l4, processedLines, groups, progressTracker);

        // Start the threads
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        Thread thread3 = new Thread(task3);
        Thread thread4 = new Thread(task4);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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

    private void processLines(List<String> lines, Set<String> processedLines, List<Set<String>> groups, ProgressTracker progressTracker) {
        for (String line : lines) {
            if (processedLines.contains(line)) {
                continue;
            }
            String[] values = splitLine(line);  // Split line into values
            boolean addedToGroup = false;

            // Check if the line belongs to an existing group
            synchronized (groups) {
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
                    Set<String> newGroup = ConcurrentHashMap.newKeySet();
                    newGroup.add(line);
                    groups.add(newGroup);
                }
            }

            // Update progress
            progressTracker.incrementProcessedLines();
        }
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
