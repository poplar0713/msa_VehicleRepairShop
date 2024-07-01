package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class JobCreated extends AbstractEvent {

    private Long id;
    private String customerName;
    private String carId;
    private String carNumber;
    private Date requestDate;
    private Integer fare;
    private String term;
    private Integer manDay;
    private Long mechanicId;
    private String mechanicName;

    public JobCreated(Receipt aggregate) {
        super(aggregate);
    }

    public JobCreated() {
        super();
    }
}
//>>> DDD / Domain Event
