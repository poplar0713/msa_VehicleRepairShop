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
    ShopRepository shopRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PartRequested'"
    )
    public void wheneverPartRequested_PartSaga(
        @Payload PartRequested partRequested
    ) {
        PartRequested event = partRequested;
        System.out.println(
            "\n\n##### listener PartSaga : " + partRequested + "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PartRecived'"
    )
    public void wheneverPartRecived_PartSaga(@Payload PartRecived partRecived) {
        PartRecived event = partRecived;
        System.out.println(
            "\n\n##### listener PartSaga : " + partRecived + "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='StockDecreased'"
    )
    public void wheneverStockDecreased_PartSaga(
        @Payload StockDecreased stockDecreased
    ) {
        StockDecreased event = stockDecreased;
        System.out.println(
            "\n\n##### listener PartSaga : " + stockDecreased + "\n\n"
        );
        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
