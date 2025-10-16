pipeline {
    agent any

    /* ---------- Parameters shown at build time ---------- */
    parameters {
        string(name: 'GIT_BRANCH',
               defaultValue: 'branch1',
               description: 'Git branch to checkout')
        string(name: 'DOCKER_TAG',
               defaultValue: 'latest',
               description: 'Docker image tag to build and run')

    }

    /* ---------- Common environment values ---------- */
    environment {
        DOCKER_IMAGE     = 'krishan'             // ✅ change to your docker image name
		
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
                bat "docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% ."
                
            }
        }
             /* ---- Stop & remove ALL existing containers ---- */
      

        /* ---- Run a fresh container ---- */
   /*     stage('Run New Container') {
            steps {
                bat """
                docker run -itd -p 8081:8080 %DOCKER_IMAGE%:%DOCKER_TAG%
                """
        
            }
        } */
    stage('Deploy to Kubernetes') {
            steps {
                echo 'Deploying to Kubernetes/Minikube...'
				bat "cd C:\Users\prana\OneDrive\Desktop\study"
                bat "kubectl apply -f manifest.yaml"
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'kubectl get pods'
                bat 'kubectl get svc'
            }
        }
    }

    post {
        success {
            echo 'Deployment Successful ✅'
        }
        failure {
            echo 'Deployment Failed ❌'
        }
    }
}
