package uno.soft.serviece.grouper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class LinesEffectiveGrouper {

    public static void main(String[] args) {
        List<String> lines = Arrays.asList(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";;\"100\"",
                "\"400\";\"456\";\"300\""
        );

        groupLines(lines);

    }

    // todo - add collection to track what groups line belongs
    public static List<Set<String>> groupLines(List<String> lines) {
        List<Set<String>> groups = new ArrayList<>();
        Map<String, Set<Element>> elementsMap = new HashMap<>();
        Map<String, LineTracker> lineTrackerMap = new HashMap<>();
        int lineIndex = 0;

        for (String line : lines) {
            String[] lineElements = line.split(";");
            int columnIndex = -1;
            for (String element : lineElements) {
                columnIndex++;
                if (element.isEmpty()) {
                    continue;
                }

                UUID uuid = UUID.randomUUID();
                elementsMap.computeIfAbsent(element, k -> new HashSet<>()).add(new Element(uuid, line, lineIndex, columnIndex));
                // todo - fix; we add to LineTracker every element UUID in line, it's wrong
                if(lineTrackerMap.containsKey(line)) {
                    lineTrackerMap.get(line).getElementUuidsSetLineBelongsTo().add(uuid);
                } else {
                    LineTracker lineTracker = new LineTracker(line);
                    lineTracker.getElementUuidsSetLineBelongsTo().add(uuid);
                    lineTrackerMap.put(line, lineTracker);
                }

            }
            lineIndex++;
        }

        for (Map.Entry<String, Set<Element>> entry : elementsMap.entrySet()) {
            if (entry.getValue().size() > 1) {}
        }

        return groups;
    }
}



