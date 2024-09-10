pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkOut([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/w4t3rcs/sonnettopizza']]])
            }
        }

        stage('Check code quality') {
            steps {
                sh 'mvn clean verify sonar:sonar' +
                        ' -Dsonar.projectKey=sonnettopizza' +
                        ' -Dsonar.projectName=\'sonnettopizza\'' +
                        ' -Dsonar.host.url=http://localhost:9000' +
                        ' -Dsonar.token=sonnettopizza'
            }
        }

        stage('Containerize') {
            steps {
                sh 'mvn clean compile jib:build'
            }
        }

        stage('Push to ECR') {
            steps {
                sh 'aws ecr get-login-password --region eu-north-1 | docker login --username AWS --password-stdin 961341518387.dkr.ecr.eu-north-1.amazonaws.com'

                sh 'docker tag w4t3rcs/sonnetto-eureka-server:latest 961341518387.dkr.ecr.eu-north-1.amazonaws.com/w4t3rcs/sonnetto-eureka-server:latest'

                sh 'docker push 961341518387.dkr.ecr.eu-north-1.amazonaws.com/w4t3rcs/sonnetto-eureka-server:latest'
            }
        }

        stage('Deploy to K8s') {
            steps {
                withKubeConfig(caCertificate: '', clusterName: '', contextName: '', credentialsId: 'K8s', namespace: '', serverUrl: '') {
                    sh 'kubectl apply -f /k8s/cloud/manifests/application'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed'
        }

        success {
            echo 'Build and tests were successful'
        }

        failure {
            echo 'Build or tests failed'
        }
    }
}