package untitled.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class StockIncreased extends AbstractEvent {

    private Long id;
    private Long partId;
    private String partName;
    private Integer stock;
    private Integer partPrice;
    private Long partId;
    private String partName;
    private Integer stock;
    private Integer partPrice;
    private Long partId;
    private String partName;
    private Integer stock;
    private Integer partPrice;

    public StockIncreased(VehicleParts aggregate) {
        super(aggregate);
    }

    public StockIncreased() {
        super();
    }
}
//>>> DDD / Domain Event
