package untitled.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import untitled.config.kafka.KafkaProcessor;
import untitled.domain.JobCreated;
import untitled.domain.JobFinished;
import untitled.domain.JobStarted;
import untitled.domain.RepairRequested;
import untitled.domain.StatusPage;

@Service
public class StatusPageViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private StatusPageRepository statusPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRepairRequested_then_CREATE_1(
        @Payload RepairRequested repairRequested
    ) {
        try {
            if (!repairRequested.validate()) return;

            // view 객체 생성
            StatusPage statusPage = new StatusPage();
            // view 객체에 이벤트의 Value 를 set 함
            statusPage.setCustomerName(repairRequested.getCustomerName());
            statusPage.setCarId(repairRequested.getCarId());
            statusPage.setRequestDate(repairRequested.getRequestDate());
            statusPage.setRecepitId(repairRequested.getId());
            // view 레파지 토리에 save
            statusPageRepository.save(statusPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenJobCreated_then_UPDATE_1(@Payload JobCreated jobCreated) {
        try {
            if (!jobCreated.validate()) return;
            // view 객체 조회

            List<StatusPage> statusPageList = statusPageRepository.findByRecepitId(
                jobCreated.getId()
            );
            for (StatusPage statusPage : statusPageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                statusPage.setMechanicName(jobCreated.getMechanicName());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenJobStarted_then_UPDATE_2(@Payload JobStarted jobStarted) {
        try {
            if (!jobStarted.validate()) return;
            // view 객체 조회

            List<StatusPage> statusPageList = statusPageRepository.findByRecepitId(
                jobStarted.getReceiptId()
            );
            for (StatusPage statusPage : statusPageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                statusPage.setJobStatus(jobStarted.getJobStatus());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenJobFinished_then_UPDATE_3(
        @Payload JobFinished jobFinished
    ) {
        try {
            if (!jobFinished.validate()) return;
            // view 객체 조회

            List<StatusPage> statusPageList = statusPageRepository.findByRecepitId(
                jobFinished.getReceiptId()
            );
            for (StatusPage statusPage : statusPageList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                statusPage.setJobStatus(jobFinished.getJobStatus());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
