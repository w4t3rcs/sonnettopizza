# Pushing microservices onto docker registry hub
mvn clean compile test jib:build
# Creating k8s cluster using Kind (https://kind.sigs.k8s.io/)
cd ./k8s/local/kind || exit
kind create cluster --name=sonnetto-microservices --config kind-config.yaml
# Deploying ECK
kubectl create -f https://download.elastic.co/downloads/eck/2.14.0/crds.yaml
kubectl apply -f https://download.elastic.co/downloads/eck/2.14.0/operator.yaml
kubectl create secret generic elasticsearch-es-elastic-user --from-literal=elastic=elastic
# Deploying needed infrastructure for microservices (kafka, redis, mysql, etc)
cd ../manifests/ || exit
kubectl apply -f ./infrastructure/
# Deploying microservices
kubectl apply -f ./application/