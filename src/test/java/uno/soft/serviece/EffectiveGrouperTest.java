package uno.soft.serviece;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class EffectiveGrouperTest {

    private EffectiveGrouper effectiveGrouper = new EffectiveGrouper();

    @Test
    void findLineGroups() {
        List<String> lines = Arrays.asList(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";;\"100\"",
                "\"400\";\"456\";\"300\""
        );

        // Expected groups:
        // - Group 1: Lines 1, 2, 3 (sharing "123" in the second column and "100" in the third column)
        // - Group 2: Line 4 (no shared values with other lines)

        // Call the method to group the lines
        List<List<String>> result = effectiveGrouper.findLineGroups(lines);

        // Prepare the expected groups
        Set<String> expectedGroup1 = new HashSet<>(Arrays.asList(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";;\"100\""
        ));

        Set<String> expectedGroup2 = new HashSet<>(Collections.singletonList(
                "\"400\";\"456\";\"300\""
        ));

        // Expected result should have two groups
        assertEquals(2, result.size(), "The number of groups should be 2");

//        // Check that both expected groups are present in the result
//        assertTrue(result.contains(expectedGroup1), "Group 1 should be present");
//        assertTrue(result.contains(expectedGroup2), "Group 2 should be present");

        print(result);
    }

    private  void print(List<List<String>> result) {
        AtomicInteger counter = new AtomicInteger();

        // Optional: Print the groups to inspect
        result.forEach(group -> {
            System.out.println("Group:" + counter.incrementAndGet());
            group.forEach(System.out::println);
            System.out.println();
        });
    }
}