차량 정비소 시스템 

1. 클라우드 아키텍쳐
![아키텍처](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/4c27eded-2fd0-4138-80a5-a084e52a3737)

Recepit(접수), mechanic(정비사), shop(정비소), vehicleparts(부품관리) 4개의 도메인으로 구성됨


2. 이벤트 스토밍
### Model
www.msaez.io/#/59277907/storming/%EC%B0%A8%EB%9F%89%EC%A0%95%EB%B9%84
![이벤트스토밍](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/602de239-7f70-4df6-9229-21b4993fe358)

### 시나리오
 1. 고객이 접수처에서 정비 요청 (원하는 정비사 및 요청 사항 등)
 2. 해당 정비사의 공수가 남아있으면 요청 접수 및 작업 생성, 공수가 부족하다면 요청 취소
 3. 정비사는 작업을 시작한 후 필요한 부품을 요청
 4. 요청한 부품의 재고가 있으면 부품 제공, 없으면 요청 취소 처리
 5. 정비사가 작업을 종료하면 요금 청구서가 생성됨



3. Dev - 보상 트랜잭션
 시나리오 2번에서 보상 트랜잭션 구현
```
    public static void repairRequest(RepairRequested repairRequested) {
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
```


5. CQRS
   ![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/0b8d70f9-d4af-414d-bf8e-a851e9898c82)
```
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
            statusPage.setReceiptId(repairRequested.getId());
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

            List<StatusPage> statusPageList = statusPageRepository.findByReceiptId(
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

            List<StatusPage> statusPageList = statusPageRepository.findByReceiptId(
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

            List<StatusPage> statusPageList = statusPageRepository.findByReceiptId(
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

```

6. 클라우드 배포
  1) AWS ECR 리포지토리 이용
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/1ab77cec-a94d-42dc-a6f4-c83da71e75ca)

![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/b5a5955b-489e-45de-b815-6e61b41876f9)


  2) eks 배포
```
# /shop/deployment.yaml
    spec:
      containers:
        - name: shop
          image: "879772956301.dkr.ecr.ap-southeast-2.amazonaws.com/user04/shop:latest"
          ports:
            - containerPort: 8080
```
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/55266809-67cd-4d5b-94c2-66a7d23337da)


7. Config map을 활용한 환경 분리
   dev환경과 prd 환경의 분리 예제 (namespace로 구별)
```
# configmap-dev.yaml

apiVersion: v1
kind: ConfigMap
metadata:
  name: service-config-dev
  namespace: dev-repairshop
data:
  receipt-url: "http://receipt-dev:8080"
  vehicleparts-url: "http://vehicleparts-dev:8080"
  mechanic-url: "http://mechanic-dev:8080"
  shop-url: "http://shop-dev:8080"

```


```
# configmap-prd.yaml
kind: ConfigMap
metadata:
  name: service-config-prod
  namespace: prd-repairshop
data:
  receipt-url: "http://receipt:8080"
  vehicleparts-url: "http://vehicleparts:8080"
  mechanic-url: "http://mechanic:8080"
  shop-url: "http://shop:8080"
```

적용
```
kubectl apply -f configmap-dev.yaml
kubectl apply -f configmap-prod.yaml
```





 


