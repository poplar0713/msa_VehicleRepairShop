package untitled.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "StatusPage_table")
@Data
public class StatusPage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long receiptId;
    private String customerName;
    private Long jobId;
    private String carNumber;
    private String carId;
    private String jobStatus;
    private Integer totalprice;
    private Date requestDate;
    private String mechanicName;
}
