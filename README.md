# 차량 정비소 시스템 

## 1. 클라우드 아키텍쳐
![아키텍처](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/4c27eded-2fd0-4138-80a5-a084e52a3737)

Recepit(접수), mechanic(정비사), shop(정비소), vehicleparts(부품관리) 4개의 도메인으로 구성됨


## 2. 이벤트 스토밍
### Model
www.msaez.io/#/59277907/storming/%EC%B0%A8%EB%9F%89%EC%A0%95%EB%B9%84
![이벤트스토밍](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/602de239-7f70-4df6-9229-21b4993fe358)

### 시나리오
   1. 고객이 접수처에서 정비 요청 (원하는 정비사 및 요청 사항 등)
   2. 해당 정비사의 공수가 남아있으면 요청 접수 및 작업 생성, 공수가 부족하다면 요청 취소
   3. 정비사는 작업을 시작한 후 필요한 부품을 요청
   4. 요청한 부품의 재고가 있으면 부품 제공, 없으면 요청 취소 처리
   5. 정비사가 작업을 종료하면 요금 청구서가 생성됨



## 3. Dev - 보상 트랜잭션
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

## 4. gateway test
로컬 환경 테스트 
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/5c4e552d-827d-4c52-8c56-f4949ceaf0df)

```
http :8088/vehicleParts id=1 partId=1 partName="테스트" stock=20 partPrice=10000
```
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/6be72515-fe0f-44ae-b6c3-f9d2e1818e95)






## 5. CQRS
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

## 6. 클라우드 배포
###  1) AWS ECR 리포지토리 이용
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/1ab77cec-a94d-42dc-a6f4-c83da71e75ca)

![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/b5a5955b-489e-45de-b815-6e61b41876f9)


###  2) eks 배포
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


## 7. Config map을 활용한 환경 분리
###  dev환경과 prd 환경의 분리 예제 (namespace로 구별)
```
 - config map 설정파일 생성
# configmap-dev.yaml

apiVersion: v1
kind: ConfigMap
metadata:
  name: service-config-dev
  namespace: dev-repairshop
data:
  receipt-url: "http://dev-receipt:8080"
  vehicleparts-url: "http://dev-vehicleparts:8080"
  mechanic-url: "http://dev-mechanic:8080"
  shop-url: "http://dev-shop:8080"
  LOG_LEVEL: "DEBUG"

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
  LOG_LEVEL: "INFO"
```

 - gateway application.yaml 파일 수정
```
spring:
  profiles: docker
  cloud:
    gateway:
      routes:
        - id: dev-receipt
          uri: http://dev-receipt:8080
          predicates:
            - Path=/receipts/**, 
        - id: dev-shop
          uri: http://dev-shop:8080
          predicates:
            - Path=/dev-shops/**, 
        - id: dev-vehicleparts
          uri: http://dev-vehicleparts:8080
          predicates:
            - Path=/dev-vehicleParts/**, 
        - id: dev-dashboard
          uri: http://dashboard:8080
          predicates:
            - Path=, 
        - id: dev-mechanic
          uri: http://dev-mechanic:8080
          predicates:
            - Path=/dev-mechanics/**,
            - id: receipt
          uri: http://receipt:8080
          predicates:
            - Path=/receipts/**, 
        - id: shop
          uri: http://shop:8080
          predicates:
            - Path=/shops/**, 
        - id: vehicleparts
          uri: http://vehicleparts:8080
          predicates:
            - Path=/vehicleParts/**, 
        - id: dashboard
          uri: http://dashboard:8080
          predicates:
            - Path=, 
        - id: mechanic
          uri: http://mechanic:8080
          predicates:
            - Path=/mechanics/**, 
        - id: frontend
          uri: http://frontend:8080
          predicates:
            - Path=/**
```


적용
```
#클러스터에 config map 적용

kubectl apply -f configmap-dev.yaml
kubectl apply -f configmap-prod.yaml


#namespace prd-repairshop 설정시 prd 환경,
#namespace dev-reparishop 설정시 dev 환경 배포로 설정

kubectl apply -f receipt/kubernetes/deployment.yaml -n prd-repairshop
kubectl apply -f receipt/kubernetes/deployment.yaml -n dev-repairshop
```

prd 환경에서 로그레벨 INFO로 적용 된 것을 확인
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/9cdbad6e-a464-429d-ac61-ea86401024ee)



## 8. 클라우드스토리지 활용 - PVC

### EFS 생성
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/163c10b2-c2ba-4cbf-a5c9-d2e4a1fa7d79)

 - SA 생성 및 IAM 연결
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/88d2a87b-ab8c-4fc4-959e-97dd1a57e929)

 - Cluster에 EFS CSI 드라이버 설치
```
helm repo add aws-efs-csi-driver https://kubernetes-sigs.github.io/aws-efs-csi-driver
helm repo update
helm upgrade -i aws-efs-csi-driver aws-efs-csi-driver/aws-efs-csi-driver \
  --namespace kube-system \
  --set image.repository=602401143452.dkr.ecr.ap-southeast-2.amazonaws.com/eks/aws-efs-csi-driver \
  --set controller.serviceAccount.create=false \
  --set controller.serviceAccount.name=efs-csi-controller-sa
```

 - EFS csi Driver로 StorageClass 생성
```
kubectl apply -f - <<EOF
kind: StorageClass
apiVersion: storage.k8s.io/v1
metadata:
  name: efs-sc
provisioner: efs.csi.aws.com
parameters:
  provisioningMode: efs-ap
  fileSystemId: $FILE_SYSTEM_ID
  directoryPerms: "700"
EOF
```
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/32474cbd-1a1d-47dd-bf65-681ab2b77368)

 - PVC 생성 및 Pod에 연결
```
kubectl apply -f - <<EOF
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: nfs-pvc
  labels:
    app: test-pvc
spec:
  accessModes:
  - ReadWriteMany
  resources:
    requests:
      storage: 1Mi
  storageClassName: efs-sc
EOF
```
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/de89fa43-3601-4270-8781-a446ca98fea6)

 - NFS 볼륨을 가지는 주문마이크로서비스 배포
```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: receipt
  labels:
    app: receipt
spec:
  replicas: 2
  selector:
    matchLabels:
      app: receipt
  template:
    metadata:
      labels:
        app: receipt
    spec:
      containers:
        - name: receipt
          image: "879772956301.dkr.ecr.ap-southeast-2.amazonaws.com/user04/receipt:latest"
          ports:
            - containerPort: 8080
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: "docker"
          readinessProbe:
            httpGet:
              path: '/actuator/health'
              port: 8080
            initialDelaySeconds: 10
            timeoutSeconds: 2
            periodSeconds: 5
            failureThreshold: 10
          livenessProbe:
            httpGet:
              path: '/actuator/health'
              port: 8080
            initialDelaySeconds: 120
            timeoutSeconds: 2
            periodSeconds: 5
            failureThreshold: 5
          volumeMounts:
            - mountPath: "/mnt/data"
              name: volume
      volumes:
        - name: volume
          persistentVolumeClaim:
            claimName: nfs-pvc
      imagePullSecrets:
        - name: ecr-secret
EOF
```

 ### 클라우드 스토리지 연결 확인
 ![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/0ff22cab-e38a-41f2-9354-bc60e500901e)



### 9. 서비스 mesh 응용

### istio 설치 
```
export ISTIO_VERSION=1.18.1
curl -L https://istio.io/downloadIstio | ISTIO_VERSION=$ISTIO_VERSION TARGET_ARCH=x86_64 sh -
cd istio-$ISTIO_VERSION
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo --set hub=gcr.io/istio-release
```

### istio svc 확인
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/f81db6ca-3219-4db5-8a10-50f9aca89053)



### kiall 설정
```
mv samples/addons/loki.yaml samples/addons/loki.yaml.old
curl -o samples/addons/loki.yaml https://raw.githubusercontent.com/msa-school/Lab-required-Materials/main/Ops/loki.yaml
kubectl apply -f samples/addons
kubectl patch svc kiali -n istio-system -p '{"spec": {"type": "LoadBalancer"}}'
kubectl get service -n istio-system
```
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/a0edba52-2fcc-4282-8e8c-690bdce73a46)


### Jager 설정
```
kubectl patch svc tracing -n istio-system -p '{"spec": {"type": "LoadBalancer"}}'
kubectl get service -n istio-system
```
![image](https://github.com/poplar0713/msa_VehicleRepairShop/assets/59277907/aeeec8e4-ae06-4ef4-a7b7-b0d52b024afc)

 


