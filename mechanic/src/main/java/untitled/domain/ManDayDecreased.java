package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ManDayDecreased extends AbstractEvent {

    private Long id;
    private Long mechanicId;
    private String mechanicName;
    private Integer manDay;

    public ManDayDecreased(Mechanic aggregate) {
        super(aggregate);
    }

    public ManDayDecreased() {
        super();
    }
}
//>>> DDD / Domain Event
