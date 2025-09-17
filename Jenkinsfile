pipeline {
    agent any

    /* ---------- Parameters shown at build time ---------- */
    parameters {
        string(name: 'GIT_BRANCH',
               defaultValue: 'main',
               description: 'Git branch to checkout')
        string(name: 'DOCKER_TAG',
               defaultValue: 'latest',
               description: 'Docker image tag to build and run')
    }

    /* ---------- Common environment values ---------- */
    environment {
        DOCKER_IMAGE     = 'Krishan'             // ✅ change to your docker image name
    }

    stages {

        /* --- 1️⃣  Checkout the selected Git branch --- */
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: "*/${params.GIT_BRANCH}"]],
                          userRemoteConfigs: [[url: 'https://github.com/pranatidasrk/docker-hello-world-spring-boot.git']]])
            }
        }

        /* --- 2️⃣  Build with Maven --- */
        stage('Maven Build') {
            steps {
                // Use the maven goals you need (e.g. clean install)
                bat 'mvn clean package'
            }
        }

        /* --- 3️⃣  Build the Docker image --- */
        stage('Docker Build') {
            steps {
                bat """
                docker build -t %DOCKER_IMAGE%:${DOCKER_VERSION} .
                """
            }
        }

        /* --- 4️⃣  Stop & remove any existing containers (no name needed) --- */
        stage('Clean Old Containers') {
            steps {
                // Stop all running containers by ID (|| exit 0 avoids error if none running)
                bat 'for /F "tokens=*" %i in (\'docker ps -q\') do docker stop %i || exit 0'
                // Remove all containers (running or stopped)
                bat 'for /F "tokens=*" %i in (\'docker ps -aq\') do docker rm %i || exit 0'
            }
        }

        /* --- 5️⃣  Run the new container --- */
        stage('Run New Container') {
            steps {
                bat """
                docker run -d --name %CONTAINER_NAME% -p 8081:8080 %DOCKER_IMAGE%:${DOCKER_VERSION}
                """
            }
        }
    }

    post {
        always {
            echo "✅ Pipeline completed. New container '%CONTAINER_NAME%' is running."
        }
    }
}
