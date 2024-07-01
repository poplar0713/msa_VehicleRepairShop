package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ManDayOver extends AbstractEvent {

    private Long id;
    private Long mechanicId;
    private String mechanicName;
    private Integer manDay;

    public ManDayOver(Mechanic aggregate) {
        super(aggregate);
    }

    public ManDayOver() {
        super();
    }
}
//>>> DDD / Domain Event
