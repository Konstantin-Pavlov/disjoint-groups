package uno.soft.service.fast_and_correct;

import uno.soft.util.ProgressTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class EffectiveGrouper {

    public List<List<String>> findLineGroups(List<String> lines) {

        if (lines == null)
            return Collections.emptyList();

        // The list that will hold groups of lines. Each group is a list of strings (lines).
        List<List<String>> linesGroups = new ArrayList<>();

        if (lines.size() < 2) {
            linesGroups.add(lines);
            return linesGroups;
        }

        int totalLines = lines.size();

        // Create and start the progress tracker
        ProgressTracker progressTracker = new ProgressTracker(totalLines);
        Thread progressThread = new Thread(progressTracker);
        progressThread.start();

        // A list where each element is a column (represented as a map).
        // The map holds key-value pairs where the key is the line element and the value is the group number it belongs to.
        List<Map<String, Integer>> columns = new ArrayList<>();

        // A map that stores information about which groups need to be merged.
        // The key is the group number, and the value is the group number it should merge with.
        Map<Integer, Integer> unitedGroups = new HashMap<>();

        // Iterate over each line from the input list.
        for (String line : lines) {
            String[] lineElements = line.split(";");
            TreeSet<Integer> groupsWithSameElements = new TreeSet<>(); // A sorted set to track the group numbers with the same elements.
            List<NewLineElement> newElements = new ArrayList<>(); // A list of new elements that haven't been mapped to groups yet.

            // Iterate over each element in the line.
            for (int elementIndex = 0; elementIndex < lineElements.length; elementIndex++) {
                String currentLineElement = lineElements[elementIndex]; // Get the current element.

                // If the current column doesn't exist in the columns list, add a new map for that column.
                if (columns.size() == elementIndex)
                    columns.add(new HashMap<>());

                // Skip empty or blank elements
                if (currentLineElement.replaceAll("\"", "").trim().isEmpty())
                    continue;

                // Get the current column from the columns list.
                Map<String, Integer> currentColumn = columns.get(elementIndex);

                // Check if this element already belongs to a group.
                Integer elementGroupNumber = currentColumn.get(currentLineElement);

                // If the element already belongs to a group, add that group number to the set of matching groups.
                if (elementGroupNumber != null) {
                    // If the element's group has been merged with another, follow the chain to find the final group.
                    while (unitedGroups.containsKey(elementGroupNumber))
                        elementGroupNumber = unitedGroups.get(elementGroupNumber); // Get the final group number after merging
                    groupsWithSameElements.add(elementGroupNumber); // Add the final group number to the set.
                } else {
                    // If the element is new (not assigned to any group yet), add it to the newElements list.
                    newElements.add(new NewLineElement(currentLineElement, elementIndex));
                }
            }

            // Determine the group number for the current line.
            int groupNumber;

            if (groupsWithSameElements.isEmpty()) {
                // If no groups match this line's elements, create a new group for this line.
                linesGroups.add(new ArrayList<>());
                groupNumber = linesGroups.size() - 1;
            } else {
                // If matching groups exist, assign the current line to the first matching group.
                groupNumber = groupsWithSameElements.first();
            }

            // For each new element found in the line, associate it with the group number.
            for (NewLineElement newLineElement : newElements) {
                columns.get(newLineElement.columnNum()).put(newLineElement.lineElement(), groupNumber);
            }

            // Merge any groups that have matching elements.
            for (int matchedGroupNumber : groupsWithSameElements) {
                if (matchedGroupNumber != groupNumber) {
                    // If another group has matching elements, merge it with the current group.
                    unitedGroups.put(matchedGroupNumber, groupNumber); // Record the merge.
                    linesGroups.get(groupNumber).addAll(linesGroups.get(matchedGroupNumber)); // Merge the two groups.
                    linesGroups.set(matchedGroupNumber, null); // Mark the old group as null since it has been merged.
                }
            }

            // Add the current line to its group.
            linesGroups.get(groupNumber).add(line);

            // Update the progress tracker for every processed line.
            progressTracker.incrementProcessedLines();
        }

        // Stop the progress tracker
        progressTracker.stop();
        try {
            progressThread.join(); // Wait for the progress thread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Remove any groups that were marked as null (i.e., groups that were merged with others).
        linesGroups.removeAll(Collections.singleton(null));

        // Return the final list of line groups.
        return linesGroups;
    }
}
