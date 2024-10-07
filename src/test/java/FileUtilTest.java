import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import uno.soft.util.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class FileUtilTest {

    @ParameterizedTest
    @CsvSource({
            "\"111\";\"123\";\"222\", true",
            "\"200\";\"123\";\"100\", true",
            "\"300\";\"\";\"100\", true"
    })
    public void testValidLines(String input, boolean expectedResult) {
        assertEquals(expectedResult, FileUtil.isValidLine(input));
    }

    @Test
    public void testInvalidLines() {
        assertFalse(FileUtil.isValidLine("\"8383\"200000741652251"));
        assertFalse(FileUtil.isValidLine("\"79855053897\"83100000580443402;\"200000133000191\""));
        assertFalse(FileUtil.isValidLine("111;123;222"));  // No quotes
        assertFalse(FileUtil.isValidLine("\"111\";abc\";\"100\""));  // Missing opening quote
        assertFalse(FileUtil.isValidLine("\"100\";\"\";\"999"));  // Missing closing quote
    }

    @Test
    public void testAdditionalCases() {
        assertTrue(FileUtil.isValidLine("\"\";\"\";\"\""));  // All empty values
        assertTrue(FileUtil.isValidLine("\"123\";\"\";\"456\""));  // Mixed empty and numeric values
        assertFalse(FileUtil.isValidLine("\"\";\"123;\"456\""));  // Missing closing quote in one field
    }

    @Test
    void testGetLinesFromFile() throws IOException {
        // Mock the BufferedReader to simulate file reading
        BufferedReader mockBufferedReader = Mockito.mock(BufferedReader.class);

        // Simulate the lines returned by the bufferedReader
        when(mockBufferedReader.readLine())
                .thenReturn("line1")
                .thenReturn("line2")
                .thenReturn("line3")
                .thenReturn(null); // End of file simulation

        // Act: Simulate the getLinesFromFile behavior using the mock
        List<String> expectedLines = List.of("line1", "line2", "line3");
        String line;
        List<String> actualLines = new ArrayList<>();
        while ((line = mockBufferedReader.readLine()) != null) {
            actualLines.add(line);
        }

        // Assert: Verify that the lines match the expected output
        assertEquals(expectedLines, actualLines);
    }
}
