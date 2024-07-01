package untitled.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import untitled.config.kafka.KafkaProcessor;
import untitled.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    MechanicRepository mechanicRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RepairRequested'"
    )
    public void wheneverRepairRequested_RepairRequest(
        @Payload RepairRequested repairRequested
    ) {
        RepairRequested event = repairRequested;
        System.out.println(
            "\n\n##### listener RepairRequest : " + repairRequested + "\n\n"
        );

        // Sample Logic //
        Mechanic.repairRequest(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RequestCancled'"
    )
    public void wheneverRequestCancled_IncreaseManDay(
        @Payload RequestCancled requestCancled
    ) {
        RequestCancled event = requestCancled;
        System.out.println(
            "\n\n##### listener IncreaseManDay : " + requestCancled + "\n\n"
        );

        // Sample Logic //
        Mechanic.increaseManDay(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
