pipeline {
    agent any

    parameters {
        string(name: 'GIT_BRANCH',
               defaultValue: 'branch1',
               description: 'Git branch to checkout')
        string(name: 'DOCKER_TAG',
               defaultValue: 'latest',
               description: 'Docker image tag to build and run')
        string(name: 'HOST',
               defaultValue: 'Enter-ip-address',
               description: 'Deploy in EC2')
    }

    environment {
        DOCKER_IMAGE = 'pranatidasrk/dev'
        K8S_MANIFEST = 'C:/Users/prana/OneDrive/Desktop/study/manifest.yaml'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: "*/${params.GIT_BRANCH}"]],
                          userRemoteConfigs: [[url: 'https://github.com/pranatidasrk/docker-hello-world-spring-boot.git']]])
            }
        }

        stage('Maven Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                // Ensure DOCKER_TAG has a valid value (fallback to 'latest')
                bat """
                if "%DOCKER_TAG%"=="" (
                    set DOCKER_TAG=latest
                )
                docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% .
                """
            }
        }

        stage('Push to Docker Hub') {
            steps {
                bat "docker push %DOCKER_IMAGE%:%DOCKER_TAG%"
            }
        }

        stage('Deploy to EC2') {
            steps {
                echo "Deploying to ${params.HOST}"
                bat """
                #ssh -i C:\\Users\\prana\\OneDrive\\Desktop\\Osakappk.ppk ec2-user@%HOST% docker run -itd -p 8080:8080 %DOCKER_IMAGE%:%DOCKER_TAG%
                ssh -i C:\\Users\\prana\\OneDrive\\Desktop\\osakakey.pem ec2-user@%HOST% mkdir testing
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
