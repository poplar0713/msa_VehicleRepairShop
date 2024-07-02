package untitled.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PartRequested extends AbstractEvent {

    private Long id;
    private String mechanicName;
    private String mechanicId;
    private String term;
    private String jobStatus;
    private Integer totalPrice;
    private Long usedPartId;
    private String usedPartName;
    private Date jobStartDate;
    private Long receiptId;

    public PartRequested(Shop aggregate) {
        super(aggregate);
    }

    public PartRequested() {
        super();
    }
}
//>>> DDD / Domain Event
