# Pushing microservices onto docker registry hub
mvn clean compile test jib:build
# Creating k8s cluster using Kind (https://kind.sigs.k8s.io/)
cd ./k8s/kind || exit
kind create cluster --name=sonnetto-microservices --config kind-config.yaml
# Creating needed infrastructure for microservices (for example, kafka, redis, mysql, etc)
cd ../manifests/ || exit
kubectl apply -f ./infrastructure/
# Creating microservices
cd ./application/ || exit
kubectl apply -f ./eureka-server/
kubectl apply -f ./config-server/
