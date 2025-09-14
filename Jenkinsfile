pipeline {
    agent any    // or agent { label 'windows' } if you have a Windows node label

    environment {
        // Adjust these for your project
        DOCKER_IMAGE = "krishana"
        IMAGE_TAG    = "V2"
        CONTAINER_NAME = "shyam1"
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
                    // Stop and remove any existing container with the same name
                    bat """
                    docker ps -a -q --filter name=%CONTAINER_NAME% | findstr . && docker stop %CONTAINER_NAME% && docker rm %CONTAINER_NAME% || echo No existing container to remove
                    """

                    //
                    bat "docker run -itd --name %CONTAINER_NAME% -p 8081:8080 %DOCKER_IMAGE%:%IMAGE_TAG%"
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
