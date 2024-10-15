package uno.soft.service.fast_and_not_correct;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ElementFullInfo {
    private UUID uuid;
    private String lineElementBelongsTo;
    private int lineIndex;
    private int ColumnIndex;
}
