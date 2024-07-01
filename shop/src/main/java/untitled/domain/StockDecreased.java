package untitled.domain;

import lombok.Data;
import lombok.ToString;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class StockDecreased extends AbstractEvent {

    private Long id;
    private Long partId;
    private String partName;
    private Integer stock;
    private Integer partPrice;
}
