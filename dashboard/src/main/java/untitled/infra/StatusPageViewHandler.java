package untitled.infra;

import untitled.domain.*;
import untitled.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StatusPageViewHandler {

//<<< DDD / CQRS
    @Autowired
    private StatusPageRepository statusPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRepairRequested_then_CREATE_1 (@Payload RepairRequested repairRequested) {
        try {

            if (!repairRequested.validate()) return;

            // view 객체 생성
            StatusPage statusPage = new StatusPage();
            // view 객체에 이벤트의 Value 를 set 함
            statusPage.setCustomerName(repairRequested.getCustomerName());
            statusPage.setRequestId(repairRequested.getId());
            statusPage.setCarId(repairRequested.getCarId());
            statusPage.setRequestDate(repairRequested.getRequestDate());
            statusPage.setCustomerName(repairRequested.getCustomerName());
            statusPage.setRequestId(repairRequested.getId());
            statusPage.setCarId(repairRequested.getCarId());
            statusPage.setRequestDate(repairRequested.getRequestDate());
            // view 레파지 토리에 save
            statusPageRepository.save(statusPage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenJobStarted_then_UPDATE_1(@Payload JobStarted jobStarted) {
        try {
            if (!jobStarted.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findByJobstatus(jobStarted.getJobStatus());
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenJobFinished_then_UPDATE_2(@Payload JobFinished jobFinished) {
        try {
            if (!jobFinished.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findByRequestId(jobFinished.getReceiptId());
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    statusPage.setJobStatus(jobFinished.getJobStatus());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenJobFinished_then_UPDATE_3(@Payload JobFinished jobFinished) {
        try {
            if (!jobFinished.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findByRequestId(jobFinished.getReceiptId());
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    statusPage.setJobStatus(jobFinished.getJobStatus());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPartRecived_then_UPDATE_4(@Payload PartRecived partRecived) {
        try {
            if (!partRecived.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findByRequestId(partRecived.getReceiptId());
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    statusPage.setTotalprice(partRecived.getTotalPrice());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPartRequestCancled_then_UPDATE_5(@Payload PartRequestCancled partRequestCancled) {
        try {
            if (!partRequestCancled.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findByRequestId(partRequestCancled.getReceiptId());
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    statusPage.setTotalprice(partRequestCancled.getTotalPrice());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenJobCreated_then_UPDATE_6(@Payload JobCreated jobCreated) {
        try {
            if (!jobCreated.validate()) return;
                // view 객체 조회

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPartRecived_then_UPDATE_7(@Payload PartRecived partRecived) {
        try {
            if (!partRecived.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findByRequestId(partRecived.getReceiptId());
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    statusPage.setTotalprice(partRecived.getTotalPrice());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPartRequestCancled_then_UPDATE_8(@Payload PartRequestCancled partRequestCancled) {
        try {
            if (!partRequestCancled.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findByRequestId(partRequestCancled.getReceiptId());
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    statusPage.setTotalprice(partRequestCancled.getTotalPrice());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenJobCreated_then_UPDATE_9(@Payload JobCreated jobCreated) {
        try {
            if (!jobCreated.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findByRequestId(jobCreated.getId());
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    statusPage.setMechanicName(jobCreated.getMechanicName());
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void when_then_UPDATE_(@Payload  ) {
        try {
            if (!.validate()) return;
                // view 객체 조회

                List<StatusPage> statusPageList = statusPageRepository.findBy();
                for(StatusPage statusPage : statusPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    statusPage.set();
                // view 레파지 토리에 save
                statusPageRepository.save(statusPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


//>>> DDD / CQRS
}

