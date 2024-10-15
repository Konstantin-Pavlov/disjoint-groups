package uno.soft.serviece.grouper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public class LinesEffectiveGrouper {

    public static void main(String[] args) {
//        List<String> lines = Arrays.asList(
//                "\"111\";\"123\";\"222\"",
//                "\"200\";\"123\";\"100\"",
//                "\"300\";;\"100\"",
//                "\"400\";\"456\";\"300\"",
//                "\"300\";\"556\";\"101\""
//        );

        List<String> lines = Arrays.asList(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";;\"100\"",
                "\"400\";\"456\";\"300\""
        );

        groupLines(lines);

    }


    public static List<Set<String>> groupLines(List<String> lines) {
        List<Set<String>> groups = new ArrayList<>();
        Map<ElementWithColumnPosition, Set<ElementFullInfo>> elementsMap = new HashMap<>();
        Map<String, LineTracker> lineTrackerMap = new HashMap<>();
        ElementWithColumnPosition elementWithColumnPosition = null;
        int lineIndex = 0;

        for (String line : lines) {
            String[] lineElements = line.split(";");
            int columnIndex = -1;
            for (String element : lineElements) {
                columnIndex++;
                if (element.isEmpty()) {
                    continue;
                }


                //todo first - element represents also its column index in elementsMap!!! (done)

                // todo - add line to lineTrackerMap before this if; in case else (elementsMap contains element, than add to lineTrackerMap)

                elementWithColumnPosition = new ElementWithColumnPosition(element, columnIndex);
                // make sure lines share one element have the same uuid
                if (!elementsMap.containsKey(elementWithColumnPosition)) {
                    UUID uuid = UUID.randomUUID();
                    HashSet<ElementFullInfo> set = new HashSet<>();
                    set.add(new ElementFullInfo(uuid, line, lineIndex, columnIndex));
                    elementsMap.put(elementWithColumnPosition, set);



                } else {
                    Optional<ElementFullInfo> first = elementsMap.get(elementWithColumnPosition).stream().findFirst();
                    if (first.isPresent()) {
                        UUID uuid = first.get().getUuid();
                        Objects.requireNonNull(elementsMap.get(elementWithColumnPosition)).add(new ElementFullInfo(uuid, line, lineIndex, columnIndex));


                    } else {
                        throw new IllegalArgumentException("Element " + element + " not found");
                    }
                }



//                elementsMap.computeIfAbsent(element, k -> new HashSet<>()).add(new Element(uuid, line, lineIndex, columnIndex));




                if (lineTrackerMap.containsKey(line)) {
                    lineTrackerMap.get(line).getElementWithColumnPositionsSet().add(elementWithColumnPosition);
                } else {
                    LineTracker lineTracker = new LineTracker(line);
                    lineTracker.getElementWithColumnPositionsSet().add(elementWithColumnPosition);
                    lineTrackerMap.put(line, lineTracker);
                }

            }

            LineTracker lineTracker = new LineTracker(line);
            lineTracker.getElementWithColumnPositionsSet().add(elementWithColumnPosition);
            lineTrackerMap.put(line, lineTracker);

            for (Map.Entry<ElementWithColumnPosition, Set<ElementFullInfo>> elementWithColumnPositionSetEntry : elementsMap.entrySet()) {
                for (ElementFullInfo elementFullInfo : elementWithColumnPositionSetEntry.getValue()) {
                    if(elementFullInfo.getLineElementBelongsTo().equalsIgnoreCase(line)) {
                        lineTracker.getElementWithColumnPositionsSet().add(elementWithColumnPosition);
                    }
                }
            }


            lineIndex++;
        }

//        for (Map.Entry<String, Set<ElementFullInfo>> entry : elementsMap.entrySet()) {
//            if (entry.getValue().size() > 1) {
//            }
//        }

        return groups;
    }
}



