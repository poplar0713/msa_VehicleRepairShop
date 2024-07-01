package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import untitled.infra.AbstractEvent;

@Data
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
}
