package uno.soft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Utility class for file operations, specifically for reading and validating lines from a GZIP-compressed file.
 * The class includes a method to read the file, validate each line, and return a list of valid lines.
 */
public class FileUtil {

    /**
     * Reads a GZIP-compressed file and returns a list of valid lines.
     * Each line is checked using the {@link #isValidLine(String)} method.
     * Only valid lines are included in the returned list.
     *
     * @return a list of valid lines from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static List<String> getLinesFromFile() throws IOException {
        File file = Path.of("file", "lng-4.txt.gz").toFile();
        System.out.println(file.exists());
        System.out.println(file.canRead());
        System.out.println(file.getName());
        List<String> lines = new ArrayList<>();

        /*
         * FileInputStream: Reads raw (compressed) bytes from the file.
         * GZIPInputStream: Decompresses those bytes as they're being read.
         * InputStreamReader: Converts the decompressed bytes into characters.
         * BufferedReader: Buffers the characters for efficient reading and provides convenient methods like readLine().
         */
        try (FileInputStream fileInputStream = new FileInputStream(file);
             GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
             InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (isValidLine(line)) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    /**
     * Checks if a given line from a file is valid. A valid line must:
     * <ul>
     *     <li>Have an even number of double quotes (").</li>
     *     <li>Each value must be enclosed in double quotes and separated by semicolons.</li>
     * </ul>
     * <p>
     * Example of valid lines:
     * <pre>
     * "111";"123";"222"
     * "200";"123";"100"
     * "300";"";"100"
     * </pre>
     * <p>
     * Example of invalid lines:
     * <pre>
     * "8383"200000741652251"
     * "79855053897"83100000580443402";"200000133000191"
     * </pre>
     *
     * @param line the line to check for validity
     * @return true if the line is valid, false otherwise
     */
    public static boolean isValidLine(String line) {
        // Check if the number of double quotes is even
        long quoteCount = line.chars().filter(ch -> ch == '"').count();
        if (quoteCount % 2 != 0) {
            return false;  // The number of quotes must be even
        }

        // Split the line by semicolons
        String[] values = line.split(";");

        // Check each value
        for (String value : values) {
            // Check if the value is enclosed in double quotes
            if (!value.startsWith("\"") || !value.endsWith("\"")) {
                return false;
            }
        }
        return true;
    }
}
