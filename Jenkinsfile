pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/w4t3rcs/sonnettopizza.git'
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

        stage('Deploy to K8s') {
            steps {
                sh ' kubectl apply -f ./k8s/manifests/infrastructure'
                sh ' kubectl apply -f ./k8s/manifests/application'
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