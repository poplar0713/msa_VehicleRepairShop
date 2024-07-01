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
    ReceiptRepository receiptRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='JobFinished'"
    )
    public void wheneverJobFinished_GenerateBill(
        @Payload JobFinished jobFinished
    ) {
        JobFinished event = jobFinished;
        System.out.println(
            "\n\n##### listener GenerateBill : " + jobFinished + "\n\n"
        );

        // Sample Logic //
        Receipt.generateBill(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ManDayOver'"
    )
    public void wheneverManDayOver_RequestReject(
        @Payload ManDayOver manDayOver
    ) {
        ManDayOver event = manDayOver;
        System.out.println(
            "\n\n##### listener RequestReject : " + manDayOver + "\n\n"
        );

        // Sample Logic //
        Receipt.requestReject(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DecreaseManDay'"
    )
    public void wheneverDecreaseManDay_CreateJob(
        @Payload DecreaseManDay decreaseManDay
    ) {
        DecreaseManDay event = decreaseManDay;
        System.out.println(
            "\n\n##### listener CreateJob : " + decreaseManDay + "\n\n"
        );

        // Sample Logic //
        Receipt.createJob(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
