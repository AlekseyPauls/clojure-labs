pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh '''alias lein=\'lein.bat\'
lein tests'''
      }
    }

  }
}