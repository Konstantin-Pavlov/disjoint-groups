package uno.soft;

import uno.soft.serviece.LinesGrouper;
import uno.soft.util.FileUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {



        List<String> lines;
        try {
            lines = FileUtil.getLinesFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        long startTime = System.nanoTime(); // Start time measurement


//        lines.stream().limit(25).forEach(System.out::println);

        LinesGrouper linesGrouper = new LinesGrouper();
        List<Set<String>> groups = linesGrouper.groupLines(lines);


        long endTime = System.nanoTime(); // End time measurement
        long duration = endTime - startTime; // Calculate the duration in nanoseconds

        System.out.println("Execution time: " + duration / 1_000_000 + " ms"); // Convert to milliseconds and print
        System.out.println("number of lines: " + lines.size());
        System.out.println("number of groups: " + groups.size()); // Convert to milliseconds and print

        long count = groups.stream()
                .filter(group -> group.size() > 1)
                .count();
        System.out.println("number of groups with more than one element: " + count);

    }
}