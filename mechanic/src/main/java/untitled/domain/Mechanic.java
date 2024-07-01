package untitled.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import untitled.MechanicApplication;
import untitled.domain.DecreaseManDay;
import untitled.domain.ManDayOver;

@Entity
@Table(name = "Mechanic_table")
@Data
//<<< DDD / Aggregate Root
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mechanicName;

    private Integer manDay;

    @PostPersist
    public void onPostPersist() {
        DecreaseManDay decreaseManDay = new DecreaseManDay(this);
        decreaseManDay.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        ManDayOver manDayOver = new ManDayOver(this);
        manDayOver.publishAfterCommit();
    }

    public static MechanicRepository repository() {
        MechanicRepository mechanicRepository = MechanicApplication.applicationContext.getBean(
            MechanicRepository.class
        );
        return mechanicRepository;
    }

    //<<< Clean Arch / Port Method
    public static void repairRequest(RepairRequested repairRequested) {
        //implement business logic here:

        /** Example 1:  new item 
        Mechanic mechanic = new Mechanic();
        repository().save(mechanic);

        DecreaseManDay decreaseManDay = new DecreaseManDay(mechanic);
        decreaseManDay.publishAfterCommit();
        ManDayOver manDayOver = new ManDayOver(mechanic);
        manDayOver.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(repairRequested.get???()).ifPresent(mechanic->{
            
            mechanic // do something
            repository().save(mechanic);

            DecreaseManDay decreaseManDay = new DecreaseManDay(mechanic);
            decreaseManDay.publishAfterCommit();
            ManDayOver manDayOver = new ManDayOver(mechanic);
            manDayOver.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseManDay(RequestCancled requestCancled) {
        //implement business logic here:

        /** Example 1:  new item 
        Mechanic mechanic = new Mechanic();
        repository().save(mechanic);

        */

        /** Example 2:  finding and process
        
        repository().findById(requestCancled.get???()).ifPresent(mechanic->{
            
            mechanic // do something
            repository().save(mechanic);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
