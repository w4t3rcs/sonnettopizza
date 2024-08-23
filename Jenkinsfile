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

        stage('Deploy') {
            steps {
                sh 'mvn jib:build'
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