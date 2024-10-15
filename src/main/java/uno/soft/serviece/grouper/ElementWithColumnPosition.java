package uno.soft.serviece.grouper;

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
