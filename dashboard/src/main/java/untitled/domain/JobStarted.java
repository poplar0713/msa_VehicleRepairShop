package untitled.domain;

import java.util.Date;

import lombok.Data;
import untitled.infra.AbstractEvent;

@Data
public class JobStarted extends AbstractEvent {

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
}
