package untitled.domain;

import lombok.Data;
import lombok.ToString;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class ManDayOver extends AbstractEvent {

    private Long id;
    private Long mechanicId;
    private String mechanicName;
    private Integer manDay;
    private Long receiptId;
}
