package uno.soft.serviece.grouper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode
public class Group {
    private String groupCommonElement;
    private Set<String> groupMembers;

    public Group() {
        this.groupCommonElement = "";
        this.groupMembers = new HashSet<>();
    }

}
