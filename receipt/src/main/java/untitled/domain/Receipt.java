package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import untitled.ReceiptApplication;
import untitled.domain.BillIssued;
import untitled.domain.JobCreated;
import untitled.domain.RepairRequested;
import untitled.domain.RequestCancled;

@Entity
@Table(name = "Receipt_table")
@Data
//<<< DDD / Aggregate Root
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerName;

    private String carId;

    private String carNumber;

    private Date requestDate;

    private Integer fare;

    private String term;

    private Integer manDay;

    private Long mechanicId;

    private String mechanicName;

    @PostPersist
    public void onPostPersist() {
        RepairRequested repairRequested = new RepairRequested(this);
        repairRequested.publishAfterCommit();

        BillIssued billIssued = new BillIssued(this);
        billIssued.publishAfterCommit();

        RequestCancled requestCancled = new RequestCancled(this);
        requestCancled.publishAfterCommit();

        JobCreated jobCreated = new JobCreated(this);
        jobCreated.publishAfterCommit();
    }

    public static ReceiptRepository repository() {
        ReceiptRepository receiptRepository = ReceiptApplication.applicationContext.getBean(
            ReceiptRepository.class
        );
        return receiptRepository;
    }

    //<<< Clean Arch / Port Method
    public static void generateBill(JobFinished jobFinished) {
        //implement business logic here:

        /** Example 1:  new item 
        Receipt receipt = new Receipt();
        repository().save(receipt);

        BillIssued billIssued = new BillIssued(receipt);
        billIssued.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(jobFinished.get???()).ifPresent(receipt->{
            
            receipt // do something
            repository().save(receipt);

            BillIssued billIssued = new BillIssued(receipt);
            billIssued.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void requestReject(ManDayOver manDayOver) {
        //implement business logic here:

        /** Example 1:  new item 
        Receipt receipt = new Receipt();
        repository().save(receipt);

        */

        /** Example 2:  finding and process
        
        repository().findById(manDayOver.get???()).ifPresent(receipt->{
            
            receipt // do something
            repository().save(receipt);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void createJob(DecreaseManDay decreaseManDay) {
        //implement business logic here:

        /** Example 1:  new item 
        Receipt receipt = new Receipt();
        repository().save(receipt);

        JobCreated jobCreated = new JobCreated(receipt);
        jobCreated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(decreaseManDay.get???()).ifPresent(receipt->{
            
            receipt // do something
            repository().save(receipt);

            JobCreated jobCreated = new JobCreated(receipt);
            jobCreated.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
