pipeline {
    agent any
    tools {
        jdk "jdk-21" // valid options are: "jdk-8", "jdk-16", "jdk-17" or "jdk-21", choose which one you need
    }
    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Build & Publish') {
            steps {
                echo 'Building & Publishing'
                sh './gradlew build publish'
            }
        }
    }
    post {
        always {
            discordSend(
                webhookURL: credentials('gt-discord-webhook'),
                thumbnail: "https://raw.githubusercontent.com/GT-Reimagined/gt-reimagined.github.io/refs/heads/main/icon.png",
                title: "GTCore ${TAG_NAME} #${BUILD_NUMBER}",
                link: env.BUILD_URL,
                result: currentBuild.currentResult,
                description: "Build: [${BUILD_NUMBER}](${env.BUILD_URL})\nStatus: ${currentBuild.currentResult}"
            )
        }
    }
}
