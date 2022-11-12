pipeline {
    agent{
        docker{
            image 'maven:3-eclipse-temurin-8'
        }
    }
    
    stages{
        stage('Build'){
            steps{
                sh 'mvn compile package'
                
                archiveArtifacts artifacts: 'target/Request-*-jar-with-dependencies.jar'
            }
        }
    }
}
