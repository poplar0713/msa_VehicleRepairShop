package untitled.external;

import java.util.Date;
import lombok.Data;

@Data
public class VehicleParts {

    private Long id;
    private Long partId;
    private String partName;
    private Integer stock;
    private Integer partPrice;
}
