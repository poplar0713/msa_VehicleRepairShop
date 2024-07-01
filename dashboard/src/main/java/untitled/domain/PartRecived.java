package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import untitled.infra.AbstractEvent;

@Data
public class PartRecived extends AbstractEvent {

    private Long receiptId;
    private Long id;
    private String mechanicName;
    private String mechanicId;
    private String term;
    private String jobStatus;
    private Integer totalPrice;
    private Long usedPartId;
    private String usedPartName;
    private Date jobStartDate;
}
