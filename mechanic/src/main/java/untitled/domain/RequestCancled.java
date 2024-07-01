package untitled.domain;

import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class RequestCancled extends AbstractEvent {

    private Long mechanicId;
    private Long id;
    private String customerName;
    private String carId;
    private String carNumber;
    private Date requestDate;
    private Integer fare;
    private String term;
    private Integer manDay;
    private String mechanicName;
}
