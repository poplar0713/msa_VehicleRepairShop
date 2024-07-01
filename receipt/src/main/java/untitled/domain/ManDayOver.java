package untitled.domain;

import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class ManDayOver extends AbstractEvent {

    private Long id;
    private Long mechanicId;
    private String mechanicName;
    private Integer manDay;
}
