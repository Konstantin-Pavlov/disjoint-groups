package uno.soft.service.fast_and_not_correct;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class LineTracker {
    private final String line;
    private final Set<ElementWithColumnPosition> elementWithColumnPositionsSet;

    public LineTracker(String line) {
        this.elementWithColumnPositionsSet = new HashSet<>();
        this.line = line;
    }
}
