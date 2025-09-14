pipeline {
    agent any

    parameters {
        // <─── Parameter user will fill when starting the job
        string(
            name: 'DOCKER_VERSION',
            defaultValue: 'latest',
            description: 'Docker image tag/version (e.g. v1.0, 1.2.3, latest)'
        )
    }
     DOCKER_IMAGE   = "premanandamaharaj"  // change to your image name
    CONTAINER_NAME = "premananda"                    // container name to run
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
