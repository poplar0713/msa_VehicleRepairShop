package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import untitled.ShopApplication;
import untitled.domain.JobFinished;
import untitled.domain.JobStarted;
import untitled.domain.PartRecived;
import untitled.domain.PartRequestCancled;
import untitled.domain.PartRequested;

@Entity
@Table(name = "Shop_table")
@Data
//<<< DDD / Aggregate Root
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mechanicName;

    private String mechanicId;

    private String term;

    private String jobStatus;

    private Integer totalPrice;

    private Long usedPartId;

    private String usedPartName;

    private Date jobStartDate;

    private Long receiptId;

    @PostPersist
    public void onPostPersist() {
        JobStarted jobStarted = new JobStarted(this);
        jobStarted.publishAfterCommit();

        JobFinished jobFinished = new JobFinished(this);
        jobFinished.publishAfterCommit();

        PartRequested partRequested = new PartRequested(this);
        partRequested.publishAfterCommit();

        PartRecived partRecived = new PartRecived(this);
        partRecived.publishAfterCommit();

        PartRequestCancled partRequestCancled = new PartRequestCancled(this);
        partRequestCancled.publishAfterCommit();
    }

    public static ShopRepository repository() {
        ShopRepository shopRepository = ShopApplication.applicationContext.getBean(
            ShopRepository.class
        );
        return shopRepository;
    }
}
//>>> DDD / Aggregate Root
