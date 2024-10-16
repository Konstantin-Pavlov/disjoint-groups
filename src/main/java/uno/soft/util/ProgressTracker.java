package uno.soft.util;

import java.util.concurrent.atomic.AtomicInteger;

public class ProgressTracker implements Runnable {
    private final int totalLines;
    private int lastPercentage;
    private String lastRemainingTimeStr;
    private final AtomicInteger processedLines;
    private final long startTime;
    private volatile boolean running;

    public ProgressTracker(int totalLines) {
        this.totalLines = totalLines;
        this.lastPercentage = 0;
        this.processedLines = new AtomicInteger(0);
        this.startTime = System.nanoTime();
        this.running = true;
    }

    public synchronized void incrementProcessedLines() {
        processedLines.incrementAndGet();
        notify(); // Notify the waiting thread
    }

    public synchronized void stop() {
        running = false;
        notify(); // Notify the waiting thread to exit
    }

    @Override
    public void run() {
        while (running) {
            synchronized (this) {
                try {
                    wait(1000); // Wait for 1 second or until notified
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            long elapsedTime = (System.nanoTime() - startTime) / 1_000_000_000;
            int percentage = (processedLines.get() * 100) / totalLines;
            String elapsedTimeStr = formatElapsedTime(elapsedTime);
            String remainingTimeStr = calculateRemainingTime(elapsedTime, percentage);

            System.out.print(
                    ConsoleColors.GREEN +
                            "\rProcessing: " + percentage + "%, Time elapsed: " + elapsedTimeStr + ", " + remainingTimeStr +
                            ConsoleColors.RESET
            );
        }
        // Final update
        System.out.println();
        long elapsedTime = (System.nanoTime() - startTime) / 1_000_000_000;
        String elapsedTimeStr = formatElapsedTime(elapsedTime);
        System.out.print(
                ConsoleColors.GREEN_BACKGROUND +
                        "Processing: 100%, Time elapsed: " + elapsedTimeStr + ", Remaining time: 0s" +
                        ConsoleColors.RESET + "\n"
        );
    }

    private String formatElapsedTime(long elapsedTime) {
        long hours = elapsedTime / 3600;
        long minutes = (elapsedTime % 3600) / 60;
        long seconds = elapsedTime % 60;
        if (hours > 0) {
            return hours + "h " + minutes + "m " + seconds + "s";
        } else if (minutes > 0) {
            return minutes + "m " + seconds + "s";
        } else {
            return seconds + "s";
        }
    }

    private String calculateRemainingTime(long elapsedTime, int percentage) {
        String result;
        if (percentage == 0) {
            result = "Calculating remaining time...";
        } else if (percentage > lastPercentage) {
            lastPercentage = percentage;
            long estimatedTotalTime = (elapsedTime * 100) / percentage;
            long remainingTime = estimatedTotalTime - elapsedTime;
            long remainingHours = remainingTime / 3600;
            long remainingMinutes = (remainingTime % 3600) / 60;
            long remainingSeconds = remainingTime % 60;
            if (remainingHours > 0) {
                result = "approximate remaining time: " + remainingHours + "h " + remainingMinutes + "m " + remainingSeconds + "s";
            } else if (remainingMinutes > 0) {
                result = "approximate remaining time: " + remainingMinutes + "m " + remainingSeconds + "s";
            } else {
                result = "approximate remaining time: " + remainingSeconds + "s";
            }
            lastRemainingTimeStr = result;
        } else {
            result = lastRemainingTimeStr;
        }
        return result;
    }
}