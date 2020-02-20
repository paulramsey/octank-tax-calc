# octank-tax-calc

Bare-bones Spring Boot microservice used for demo purposes. Not supported - no warranty explicit or implied.

API accepts cart item data with subtotal and returns tax rate and total tax.

Example cart data POST input:
```json
{
    "cartId": "TestUserCart",
    "productId": "10001",
    "quantity": "5",
    "subtotal": "108.11"
}
```

Example API response:
```json
{
    "responseObject": {
        "totalTax": "6.648765",
        "taxRate": "0.0615"
    }
}
```

Sample curl POST script:
```bash
curl -w "\n" \
   -H "Accept: application/json" \
   -H "Content-Type:application/json" \
   -X POST \
   --data '{"cartId": "TestUserCart", "productId": "10001", "quantity": "5", "subtotal": "108.11"}' \
   "http://localhost:8080/tax-calc/"
```

## Generate Load

To generate load, you can run a GET instead of a POST. This will run Pi calculations to simulate processing time and will return randomized taxRate and totalTax in JSON format:  
```json
{
    "responseObject": {
        "taxRate": "0.0807",
        "totalTax": "86.22"
    }
}
```

Load generation script:
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
   curl -w "\n" http://tax:8080/tax-calc/ &
   echo "  ";
   x=$(( $x + 1 ));
done
```

## Pre-requisites
- Install Maven and Java 8
- Install and configure AWS CLI 
- Clone repo
- Update `<aws-account-number>` with your real AWS account number in the deploy steps below and in `./deploy/kubernetes/ecr-login.sh`. Change the AWS region throughout if desired.

## Run locally via Maven
- From project root, run: `./mvnw spring-boot:run`
- Navigate to: `http://localhost:8080/tax-calc/`

## Build Docker container 
- From project root, run: `./mvnw clean package && docker build --tag=tax-calc .`

## Run locally via Docker with Wildfly
- Run: `docker run -it -p 8080:8080 tax-calc`  
- Navigate to: `http://localhost:8080/tax-calc/`

## Deploy to Kubernetes with Wildfly
- Push image to ECR:  
`$(aws ecr get-login --no-include-email --region us-east-2)`  
`docker tag tax-calc:latest <aws-account-number>.dkr.ecr.us-east-2.amazonaws.com/tax-calc:latest`  
`docker push <aws-account-number>.dkr.ecr.us-east-2.amazonaws.com/tax-calc:latest` 
- Deploy to K8s cluster:  
`./deploy/kubernetes/ecr-login.sh`   
`kubectl apply -f deploy/kubernetes/kube-deploy.yaml`
- Navigate to: `http://localhost:30001/tax-calc/`

## Run X-Ray daemon locally for development:
```bash
cd deploy/xray-local
docker build -t xray-daemon .
docker run \
      --attach STDOUT \
      -v ~/.aws/:/root/.aws/:ro \
      -e AWS_REGION=us-east-2 \
      --name xray-daemon \
      -p 2000:2000/udp \
      -p 2000:2000/tcp \
      xray-daemon -o
```

## Deploy X-Ray daemon image to ECR
- Push image to ECR:  
`$(aws ecr get-login --no-include-email --region us-east-2)`  
`docker tag xray-daemon:latest <aws-account-number>.dkr.ecr.us-east-2.amazonaws.com/xray-daemon:latest`  
`docker push <aws-account-number>.dkr.ecr.us-east-2.amazonaws.com/xray-daemon:latest` 