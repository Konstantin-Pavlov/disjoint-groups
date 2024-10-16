package uno.soft;

import uno.soft.service.fast_and_correct.EffectiveGrouper;
import uno.soft.util.ConsoleColors;
import uno.soft.util.FileUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        List<String> lines;

        if (args.length > 0) {
            System.out.println(ConsoleColors.ANSI_PURPLE + "Project has been launched with command line arguments" + ConsoleColors.ANSI_RESET);
            System.out.println(ConsoleColors.BLUE_BOLD + "Arguments: " + Arrays.toString(args) + ConsoleColors.RESET);
            try {
                lines = FileUtil.getLinesFromTxtFile(args[0]);
            } catch (IOException e) {
                System.err.println(ConsoleColors.RED_BACKGROUND + "Error reading file -> " + e.getMessage() + ConsoleColors.RESET);
                return;
            }
        } else {
            System.out.println(ConsoleColors.ANSI_PURPLE + "Project has been launched without command line arguments" + ConsoleColors.ANSI_RESET);
            try {
                lines = FileUtil.getLinesFromGZIPFile();
            } catch (IOException e) {
                System.err.println(ConsoleColors.RED_BACKGROUND + "Error reading file -> " + e.getMessage() + ConsoleColors.RESET);
                return;
            }
        }

        long startTime = System.nanoTime(); // Start time measurement

        EffectiveGrouper effectiveGrouper = new EffectiveGrouper();
        List<List<String>> groups = effectiveGrouper.findLineGroups(lines);

        long endTime = System.nanoTime(); // End time measurement
        long duration = endTime - startTime; // Calculate the duration in nanoseconds

        String formattedDuration = getFormattedDuration(duration);

        System.out.println(ConsoleColors.ANSI_CYAN + "Execution time: " + formattedDuration + ConsoleColors.RESET); // Print formatted duration
        System.out.println(ConsoleColors.ANSI_CYAN + "number of lines: " + lines.size() + ConsoleColors.RESET);
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
        return String.format("%02dh:%02dm:%02ds:%03dms", hours, minutes, seconds, milliseconds);
    }
}