package uno.soft;

import uno.soft.util.FileUtil;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        long startTime = System.nanoTime(); // Start time measurement


        List<String> lines;
        try {
            lines = FileUtil.getLinesFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println(lines.size());

        lines.stream().limit(25).forEach(System.out::println);

        long endTime = System.nanoTime(); // End time measurement
        long duration = endTime - startTime; // Calculate the duration in nanoseconds

        System.out.println("Execution time: " + duration / 1_000_000 + " ms"); // Convert to milliseconds and print

    }
}