pipeline {
    agent any

    environment {
        // Change these to match your project and registry details
        DOCKER_IMAGE = "radha"
        IMAGE_TAG    = "V1"
    }

    stages {
        stage('Checkout Git Repo') {
            steps {
                git branch: 'branch1', url: 'https://github.com/pranatidasrk/docker-hello-world-spring-boot.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // For Linux agents
                bat 'mvn clean package'
                // For Windows agents, use: bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image with a tag
                    bat "docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} ."
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully! Docker image: ${DOCKER_IMAGE}:${IMAGE_TAG}"
        }
        failure {
            echo "❌ Pipeline failed. Check the build logs."
        }
    }
}
