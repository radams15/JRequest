pipeline {
    agent{
        docker{
            image 'maven:3-eclipse-temurin-8'
        }
    }
    
    stages{
        stage('Build'){
            steps{
                sh 'mvn compile package -Dmaven.compiler.source=1.4 -Dmaven.compiler.target=1.4'
                
                archiveArtifacts artifacts: 'target/Request-*-jar-with-dependencies.jar'
            }
        }
    }
}
