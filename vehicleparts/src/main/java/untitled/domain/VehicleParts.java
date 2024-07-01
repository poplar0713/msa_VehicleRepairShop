package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import untitled.VehiclepartsApplication;
import untitled.domain.StockDecreased;
import untitled.domain.StockIncreased;

@Entity
@Table(name = "VehicleParts_table")
@Data
//<<< DDD / Aggregate Root
public class VehicleParts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long partId;

    private String partName;

    private Integer stock;

    private Integer partPrice;

    @PostPersist
    public void onPostPersist() {
        StockDecreased stockDecreased = new StockDecreased(this);
        stockDecreased.publishAfterCommit();

        StockIncreased stockIncreased = new StockIncreased(this);
        stockIncreased.publishAfterCommit();
    }

    public static VehiclePartsRepository repository() {
        VehiclePartsRepository vehiclePartsRepository = VehiclepartsApplication.applicationContext.getBean(
            VehiclePartsRepository.class
        );
        return vehiclePartsRepository;
    }
}
//>>> DDD / Aggregate Root
