package untitled.domain;

import java.util.*;
import lombok.*;
import untitled.domain.*;
import untitled.infra.AbstractEvent;

@Data
@ToString
public class StockDecreased extends AbstractEvent {

    private Long id;
    private Long partId;
    private String partName;
    private Integer stock;
    private Integer partPrice;
    private Long partId;
    private String partName;
    private Integer stock;
    private Integer partPrice;
}
