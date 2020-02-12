# octank-tax-calc

Bare-bones spring boot microservice used for demo purposes. Not supported - no warranty explicit or implied.

## Pre-requisites
- Install Maven and Java 8
- Clone repo

## Run locally via Maven
- Run: `./mvnw spring-boot:run`
- Navigate to: `http://localhost:8080/tax-calc`

## Build Docker container 
- Run: `./mvnw clean package && docker build --tag=tax-calc .`

## Run locally via Docker with Wildfly
- Run: `docker run -it -p 8080:8080 tax-calc`  
- Navigate to: `http://localhost:8080/tax-calc`

## Deploy to Kubernetes with Wildfly
- Push image to ECR:  
`$(aws ecr get-login --no-include-email --region us-east-2)`  
`docker tag tax-calc:latest 715977739758.dkr.ecr.us-east-2.amazonaws.com/tax-calc:latest`  
`docker push 715977739758.dkr.ecr.us-east-2.amazonaws.com/tax-calc:latest` 
- Deploy to K8s cluster:  
`./deploy/kubernetes/ecr-login.sh`   
`kubectl apply -f deploy/kubernetes/kube-deploy.yaml`
- Navigate to: `http://localhost:30001/tax-calc/`
