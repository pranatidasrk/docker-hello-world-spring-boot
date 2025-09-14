pipeline {
    agent any    // or agent { label 'windows' } if you have a Windows node label

    environment {
        // Adjust these for your project
        DOCKER_IMAGE = "radha"
        IMAGE_TAG    = "V1"
        CONTAINER_NAME = "shyam"
    }

    stages {

        stage('Checkout Git Repo') {
            steps {
                // Replace branch and URL with your repository details
                git branch: 'branch1', url: 'https://github.com/pranatidasrk/docker-hello-world-spring-boot.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // Use Windows batch commands
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_IMAGE%:%IMAGE_TAG% ."
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                   
                    bat "docker run -itd --name %CONTAINER_NAME% -p 8080:8080 %DOCKER_IMAGE%:%IMAGE_TAG%"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
            echo "Docker image: %DOCKER_IMAGE%:%IMAGE_TAG%"
            echo "Container '%CONTAINER_NAME%' is running and mapped to port 8080."
        }
        failure {
            echo "❌ Pipeline failed. Check the console logs for errors."
        }
    }
}
