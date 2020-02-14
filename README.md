# octank-tax-calc

Bare-bones spring boot microservice used for demo purposes. Not supported - no warranty explicit or implied.

## Pre-requisites
- Install Maven and Java 8
- Clone repo

## Run locally via Maven
- Run: `./mvnw spring-boot:run`
- Navigate to: `http://localhost:8080/tax-calc/`

## Build Docker container 
- Run: `./mvnw clean package && docker build --tag=tax-calc .`

## Run locally via Docker with Wildfly
- Run: `docker run -it -p 8080:8080 tax-calc`  
- Navigate to: `http://localhost:8080/tax-calc/`

## Deploy to Kubernetes with Wildfly
- Push image to ECR:  
`$(aws ecr get-login --no-include-email --region us-east-2)`  
`docker tag tax-calc:latest 715977739758.dkr.ecr.us-east-2.amazonaws.com/tax-calc:latest`  
`docker push 715977739758.dkr.ecr.us-east-2.amazonaws.com/tax-calc:latest` 
- Deploy to K8s cluster:  
`./deploy/kubernetes/ecr-login.sh`   
`kubectl apply -f deploy/kubernetes/kube-deploy.yaml`
- Navigate to: `http://localhost:30001/tax-calc/`

## Generate some load:
- Place the script below in a file called `tax-calc-generate-load.sh`.
- Call the script with a single argument that defines how many times to call the service: `./tax-calc-generate-load.sh 1000`

```bash
#!/bin/bash

if [ $# -eq 0 ]
then
   echo "Error: You must provide an interger in position 1 to indicate the number of calls the script should make to the tax-calc service."
   exit
fi

x=0;
while [ $x -le $1 ];
do
   curl http://tax:8080/tax-calc/;
   echo " ";
   x=$(( $x + 1 ));
done
```
