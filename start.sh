mvn clean compile jib:build
#minikube start
#cd ./k8s/ || exit
#kubectl apply -f ./mysql/
#kubectl apply -f ./eureka-server/
#kubectl apply -f ./config-server/
#kubectl apply -f ./user-service/
#kubectl apply -f ./api-gateway/
docker compose up --abort-on-container-exit