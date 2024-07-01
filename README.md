# 

## Model
www.msaez.io/#/59277907/storming/%EC%B0%A8%EB%9F%89%EC%A0%95%EB%B9%84

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- receipt
- shop
- vehicleparts
- dashboard
- mechanic


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- receipt
```
 http :8088/receipts id="id" customerName="customerName" carId="carId" carNumber="carNumber" requestDate="requestDate" fare="fare" term="term" manDay="manDay" mechanicId="mechanicId" mechanicName="mechanicName" 
```
- shop
```
 http :8088/shops id="id" mechanicName="mechanicName" mechanicId="mechanicId" term="term" jobStatus="jobStatus" totalPrice="totalPrice" usedPartId="usedPartId" usedPartName="usedPartName" jobStartDate="jobStartDate" receiptId="receiptId" 
```
- vehicleparts
```
 http :8088/vehicleParts id="id" partId="partId" partName="partName" stock="stock" partPrice="partPrice" 
```
- dashboard
```
```
- mechanic
```
 http :8088/mechanics id="id" mechanicName="mechanicName" manDay="manDay" 
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

