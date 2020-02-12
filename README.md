# octank-tax-calc

Bare-bones spring boot microservice used for demo purposes. Not supported - no warranty explicit or implied.

## Deploy locally:
- Install Maven and Java 8
- Clone repo
- Run:  
`./mvnw clean package`  
`./mvnw spring-boot:run`
- Navigate to: `http://localhost:8080/tax-calc`

## Build Docker container
- Build:  
`docker build --tag=tax-calc .`
- Push to ECR:  
`$(aws ecr get-login --no-include-email --region us-east-2)`  
`docker tag tax-calc:latest 715977739758.dkr.ecr.us-east-2.amazonaws.com/tax-calc:latest`  
`docker push 715977739758.dkr.ecr.us-east-2.amazonaws.com/tax-calc:latest` 
- Deploy to Kubernetes:  
`./deploy/kubernetes/ecr-login.sh` (NOTE: This command is only required if deploying to local K8s cluster)  
`kubectl apply -f deploy/kubernetes/kube-deploy.yaml`
