package uno.soft.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

/**
 * Utility class for file operations, specifically for reading and validating lines from a GZIP-compressed file.
 * The class includes a method to read the file, validate each line, and return a list of valid lines.
 */
public class FileUtil {

    private static final String PROPERTIES_FILE = "application.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("Failed to load properties file: " + PROPERTIES_FILE);
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading properties file: " + PROPERTIES_FILE, e);
        }
    }

    private static final String FILE_FOLDER = PROPERTIES.getProperty("file.folder");
    private static final String FILE_NAME = PROPERTIES.getProperty("file.name");
    private static final String FILE_URL = PROPERTIES.getProperty("download.url");

    /**
     * Reads a GZIP-compressed file and returns a list of valid lines.
     * If the file is not present, it will download the file from the provided URL.
     *
     * @return a list of valid lines from the file
     * @throws IOException if an I/O error occurs while reading or downloading the file
     */
    public static List<String> getLinesFromGZIPFile() throws IOException {
        Path filePath = Paths.get(FILE_FOLDER, FILE_NAME);
        File file = filePath.toFile();

        // Check if file exists; if not, download it
        if (!file.exists()) {
            System.out.println("File not found, downloading...");
            downloadFile(filePath);
            System.out.println("File downloaded successfully.");
        }

        fileCheck(file, filePath);

        infoMessage(file);

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

            readLinesFromBufferedReader(bufferedReader, lines);
        }
        return lines;
    }

    /**
     * Reads a txt or CSV file and returns a list of valid lines.
     *
     * @param filePath the path to the txt or CSV file
     * @return a list of valid lines from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static List<String> getLinesFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        File file = path.toFile();

        fileCheck(file, path);

        infoMessage(file);

        List<String> lines = new ArrayList<>();

        /*
         * FileInputStream: Reads raw bytes from the file.
         * InputStreamReader: Converts the bytes into characters.
         * BufferedReader: Buffers the characters for efficient reading and provides convenient methods like readLine().
         */
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            readLinesFromBufferedReader(bufferedReader, lines);
        }
        return lines;
    }

    /**
     * Writes groups of sortedGroups to a specified file. If the file does not exist, it will be created.
     * If the file exists, its content will be overridden.
     *
     * @param fileName     the name of the file to write to
     * @param sortedGroups the groups to write to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public static void writeLinesToFile(
            String fileName,
            String numberOfLines,
            String numberOfGroups,
            String numberOfGroupsWithMoreThanOneElement,
            List<List<String>> sortedGroups
    ) throws IOException {
        System.out.println("trying to write to file: " + fileName);
        Path filePath = Paths.get(FILE_FOLDER, fileName);
        File file = filePath.toFile();

        if (!Files.exists(filePath.getParent())) {
            System.out.printf("directory %s doesn't exist, creating...\n", filePath);
            Files.createDirectories(filePath.getParent());
        } else {
            System.out.printf("directory %s already exists, skipping...\n", filePath);
        }

        if (!file.exists()) {
            System.out.println("File not found, creating...");
            if (file.createNewFile()) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("fail to create file.");
            }
        } else {
            System.out.println("File already exists. Rewriting...");
        }

        fileCheck(file, filePath);

        String text = String.format("printing %d groups", sortedGroups.size());
        String border = "*".repeat(text.length() + 4);
        String title = border + "\n* " + text + " *\n" + border;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            printTitle(numberOfLines, numberOfGroups, numberOfGroupsWithMoreThanOneElement, writer, title);
            int groupCounter = 1;
            for (List<String> group : sortedGroups) {
                writer.write(String.format("group number: %d | group size: %d", groupCounter++, group.size()));
                writer.newLine();

                for (String line : group) {
                    writer.write(line);
                    writer.newLine();
                }

                writer.write("\n" + "*".repeat(42) + "\n");
                writer.newLine();
            }
        }
        System.out.println("File written successfully.");
    }

    private static void printTitle(String numberOfLines, String numberOfGroups, String numberOfGroupsWithMoreThanOneElement, BufferedWriter writer, String title) throws IOException {
        writer.write(numberOfLines);
        writer.newLine();
        writer.write(numberOfGroups);
        writer.newLine();
        writer.write(numberOfGroupsWithMoreThanOneElement);
        writer.newLine();
        writer.newLine();
        writer.write(title);
        writer.newLine();
        writer.newLine();
    }

    /**
     * Reads lines from a BufferedReader and adds valid lines to the provided list.
     *
     * @param bufferedReader the BufferedReader to read lines from
     * @param lines          the list to which valid lines will be added
     * @throws IOException if an I/O error occurs while reading from the BufferedReader
     */
    private static void readLinesFromBufferedReader(BufferedReader bufferedReader, List<String> lines) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (isValidLine(line)) {
                lines.add(line);
            }
        }
    }

    /**
     * Checks if a file exists and is readable.
     *
     * @param file     the file to check
     * @param filePath the path to the file
     * @throws IOException if the file does not exist or cannot be read
     */
    private static void fileCheck(File file, Path filePath) throws IOException {
        // Check if file exists
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        // Check if file is readable
        if (!file.canRead()) {
            throw new IOException("File cannot be read: " + filePath);
        }
    }

    /**
     * Prints information about a file.
     *
     * @param file the file to print information about
     */
    private static void infoMessage(File file) {
        System.out.println("File exists: " + file.exists());
        System.out.println("Can read file: " + file.canRead());
        System.out.println("File name: " + file.getName());
        System.out.println("file absolute path: " + file.getAbsolutePath());
    }

    /**
     * Downloads a file from the specified URL and saves it to the given file path.
     *
     * @param savePath the path where the file should be saved
     * @throws IOException if an I/O error occurs during download
     */
    private static void downloadFile(Path savePath) throws IOException {
        try (InputStream in = new URI(FileUtil.FILE_URL).toURL().openStream()) {
            Files.createDirectories(savePath.getParent());  // Ensure parent directories exist
            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IOException("Invalid URL: " + FileUtil.FILE_URL, e);
        }
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
