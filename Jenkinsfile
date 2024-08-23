pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/w4t3rcs/sonnettopizza.git'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Containerize') {
            steps {
                sh 'mvn jib:build'
            }
        }

        stage('Deploy to K8s') {
            steps {
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