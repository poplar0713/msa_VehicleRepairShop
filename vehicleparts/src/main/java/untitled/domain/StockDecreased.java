package untitled.domain;

import lombok.Data;
import lombok.ToString;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class StockDecreased extends AbstractEvent {

    private Long id;
    private Long partId;
    private String partName;
    private Integer stock;
    private Integer partPrice;
    
    public StockDecreased(VehicleParts aggregate) {
        super(aggregate);
    }

    public StockDecreased() {
        super();
    }
}
//>>> DDD / Domain Event
