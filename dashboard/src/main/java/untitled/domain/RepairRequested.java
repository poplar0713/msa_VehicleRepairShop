package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import untitled.infra.AbstractEvent;

@Data
public class RepairRequested extends AbstractEvent {

    private Long id;
    private Long receiptId;
    private String customerName;
    private String carId;
    private String carNumber;
    private Date requestDate;
    private String term;
    private Integer fare;
    private Integer manDay;
    private Long mechanicId;
    private String mechanicName;
}
