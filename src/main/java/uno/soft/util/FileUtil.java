package uno.soft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class FileUtil {
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
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * example:<br>
     * <br>
     * valid lines: <br>
     * "111";"123";"222" <br>
     * "200";"123";"100" <br>
     * "300";"";"100" <br>
     * <p>
     * invalid lines <br>
     * "8383"200000741652251" <br>
     * "79855053897"83100000580443402";"200000133000191" <br>
     *
     * @param line
     * @return
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
