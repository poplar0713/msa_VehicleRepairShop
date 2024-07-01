package untitled.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Table;

import lombok.Data;
import untitled.MechanicApplication;

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
        ManDayDecreased decreaseManDay = new ManDayDecreased(this);
        decreaseManDay.publishAfterCommit();

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

        repository().findById(Long.valueOf(repairRequested.getId())).ifPresent(mechanic->{
            if(mechanic.getManDay() >= repairRequested.getManDay()){
                mechanic.setManDay(mechanic.getManDay() - repairRequested.getManDay()); 
                repository().save(mechanic);

                ManDayDecreased manDayDecreased = new ManDayDecreased(mechanic);
                manDayDecreased.publishAfterCommit();

            }else {
                ManDayOver manDayOver = new ManDayOver(mechanic);
                manDayOver.setReceiptId(repairRequested.getId()); 
                manDayOver.publishAfterCommit();
            }
            
        });


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
