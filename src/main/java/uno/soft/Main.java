package uno.soft;

import uno.soft.serviece.LinesGrouper;
import uno.soft.util.ConsoleColors;
import uno.soft.util.FileUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

        List<String> testSubList = lines.subList(0, 100001);

        List<Set<String>> groups = linesGrouper.groupLines(testSubList);


        long endTime = System.nanoTime(); // End time measurement
        long duration = endTime - startTime; // Calculate the duration in nanoseconds

        String formattedDuration = getFormattedDuration(duration);

        System.out.println(ConsoleColors.ANSI_CYAN + "Execution time: " + formattedDuration + ConsoleColors.RESET); // Print formatted duration
        System.out.println(ConsoleColors.ANSI_CYAN + "number of lines: " + testSubList.size() + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ANSI_CYAN + "number of groups total: " + groups.size() + ConsoleColors.RESET);

        long count = groups.stream()
                .filter(group -> group.size() > 1)
                .count();

        System.out.println(ConsoleColors.ANSI_CYAN + "number of groups with more than one element: " + count + ConsoleColors.RESET);

    }

    private static String getFormattedDuration(long duration) {
        // Convert duration to hours, minutes, seconds, and milliseconds
        long hours = TimeUnit.NANOSECONDS.toHours(duration);
        long minutes = TimeUnit.NANOSECONDS.toMinutes(duration) % 60;
        long seconds = TimeUnit.NANOSECONDS.toSeconds(duration) % 60;
        long milliseconds = TimeUnit.NANOSECONDS.toMillis(duration) % 1000;

        // Format the execution time
        String formattedDuration = String.format("%02dh:%02dm:%02ds:%03dms", hours, minutes, seconds, milliseconds);
        return formattedDuration;
    }
}