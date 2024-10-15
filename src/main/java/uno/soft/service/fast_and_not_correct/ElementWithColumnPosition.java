package uno.soft.service.fast_and_not_correct;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ElementWithColumnPosition {
    private String value;
    private int columnIndex;
}
