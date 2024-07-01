package untitled.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RepairRequested extends AbstractEvent {

    private Long id;
    private String customerName;
    private String carId;
    private String carNumber;
    private Date requestDate;
    private String term;
    private Integer fare;
    private Integer manDay;
    private Long mechanicId;
    private String mechanicName;

    public RepairRequested(Receipt aggregate) {
        super(aggregate);
    }

    public RepairRequested() {
        super();
    }
}
//>>> DDD / Domain Event
