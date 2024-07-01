package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "StatusPage_table")
@Data
public class StatusPage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String customerName;
    private Long requestId;
    private Long jobId;
    private String carNumber;
    private String carId;
    private String jobStatus;
    private Integer totalprice;
    private Date requestDate;
    private String mechanicName;
}
