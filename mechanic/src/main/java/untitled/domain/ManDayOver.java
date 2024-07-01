package untitled.domain;

import lombok.Data;
import lombok.ToString;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ManDayOver extends AbstractEvent {

    private Long id;
    private Long mechanicId;
    private String mechanicName;
    private Integer manDay;
    private Long receiptId;

    public ManDayOver(Mechanic aggregate) {
        super(aggregate);
    }

    public ManDayOver() {
        super();
    }
}
//>>> DDD / Domain Event
