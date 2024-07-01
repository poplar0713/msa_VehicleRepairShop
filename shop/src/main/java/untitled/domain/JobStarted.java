package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class JobStarted extends AbstractEvent {

    public JobStarted(Shop aggregate) {
        super(aggregate);
    }

    public JobStarted() {
        super();
    }
}
//>>> DDD / Domain Event
