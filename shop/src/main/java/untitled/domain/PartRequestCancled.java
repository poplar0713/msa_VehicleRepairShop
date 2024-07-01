package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PartRequestCancled extends AbstractEvent {

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

    public PartRequestCancled(Shop aggregate) {
        super(aggregate);
    }

    public PartRequestCancled() {
        super();
    }
}
//>>> DDD / Domain Event
