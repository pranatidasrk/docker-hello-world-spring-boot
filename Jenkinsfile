pipeline {
    agent any

    parameters {
        string(name: 'GIT_BRANCH', defaultValue: 'branch1', description: 'Git branch to checkout')
        string(name: 'DOCKER_TAG', defaultValue: 'latest', description: 'Docker image tag to build and run')
        string(name: 'HOST', defaultValue: 'Enter-ec2-public-ip', description: 'EC2 host IP address')
    }

    environment {
        DOCKER_IMAGE = 'pranatidasrk/dev'
        K8S_MANIFEST = 'C:/Users/prana/OneDrive/Desktop/study/manifest.yaml'
        PEM_PATH = 'C:/Users/prana/OneDrive/Desktop/Osakappk.pem' // use PEM, not PPK
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: "*/${params.GIT_BRANCH}"]],
                    userRemoteConfigs: [[url: 'https://github.com/pranatidasrk/docker-hello-world-spring-boot.git']]
                ])
            }
        }

        stage('Maven Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                bat "docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                        echo Logging into DockerHub...
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                        docker push %DOCKER_IMAGE%:%DOCKER_TAG%
                    """
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                echo "🚀 Deploying to ${params.HOST}"
                // Using PowerShell here to make SSH work reliably on Windows Jenkins
                powershell """
                    ssh -i '${env.PEM_PATH}' -o StrictHostKeyChecking=no ec2-user@${params.HOST} `
                    "docker pull ${env.DOCKER_IMAGE}:${params.DOCKER_TAG} && `
                     docker stop app || true && `
                     docker rm app || true && `
                     docker run -d --name app -p 8080:8080 ${env.DOCKER_IMAGE}:${params.DOCKER_TAG}"
                """
            }
        }
    }

    post {
        success {
            echo '✅ Deployment Successful'
        }
        failure {
            echo '❌ Deployment Failed'
        }
    }
}
