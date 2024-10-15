package uno.soft.serviece.grouper;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class LineTracker {
    private final String line;
    private final Set<UUID> elementUuidsSetLineBelongsTo;

    public LineTracker(String line) {
        this.elementUuidsSetLineBelongsTo = new HashSet<>();
        this.line = line;
    }
}
