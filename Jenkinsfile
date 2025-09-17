pipeline {
    agent any

    /* ---------- Parameters ---------- */
    parameters {
        string(name: 'DOCKER_TAG', defaultValue: 'V1', description: 'Docker image tag')
    }

    environment {
        // Adjust these for your project
        DOCKER_IMAGE = "krishana"
    }

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
                bat "docker build -t %DOCKER_IMAGE%:%DOCKER_VERSION% ."
            }
        }

        /* ---- Stop & remove ALL existing containers ---- */
        stage('Clean Old Containers') {
            steps {
                // Stop all running containers by ID
                bat 'for /F "tokens=*" %i in (\'docker ps -q\') do docker stop %i || exit 0'
                // Remove all containers (running or stopped)
                bat 'for /F "tokens=*" %i in (\'docker ps -aq\') do docker rm %i || exit 0'
            }
        }

        /* ---- Run a fresh container ---- */
        stage('Run New Container') {
            steps {
                bat """
                docker run -itd -p 8081:8080 %DOCKER_IMAGE%:%DOCKER_VERSION%
                """
            }
        }
    }

    post {
        always {
            echo "Pipeline finished."
        }
    }
}
