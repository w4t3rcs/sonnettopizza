minikube start
minikube status
mvn clean compile jib:build
cd ./k8s/ || exit
kubectl apply -f ./mysql/
kubectl apply -f ./eureka-server/
kubectl apply -f ./config-server/
kubectl apply -f ./user-service/
kubectl apply -f ./api-gateway/