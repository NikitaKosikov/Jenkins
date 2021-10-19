pipeline {
    agent any

    tools{
	gradle "Gradle"
    }	

    stages {
        stage('Compile') {
            steps {
                gradlew('clean', 'classes')
            }
        }
        stage('Unit Tests') {
            steps {
                gradlew('test')
            }
            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }

	stage('Code Analisis') {
		 environment {
        		scannerHome = tool 'SonarQubeScanner'
    		}
  		  steps {
      			  withSonarQubeEnv('sonarqube') {
          		  bat "${scannerHome}/bin/sonar-scanner"
       			 }
    		}
	}
        
        stage('Assemble') {
            steps {
                gradlew('assemble')
            }
        }
       
        stage('Deploy to Production') {

	    def tomcatWeb = 'C:\Users\Nikita_Kosikov\Desktop\apache-tomcat-9.0.54\webapps'
	    def tomcatBin = 'C:\Users\Nikita_Kosikov\Desktop\apache-tomcat-9.0.54\bin'
       
            steps {
		bat "copy target\\Gift-Certificate.war \"${tomcatWeb}\\Gift-Certificate.war""
                sleep(time:5,unit:"SECONDS")
		bat "${tomcatBin}\\startup.bat"
 		sleep(time:100,unit:"SECONDS")
            }
        }
    }
    post {
        failure {
            mail to: 'benjamin.muschko@gmail.com', subject: 'Build failed', body: 'Please fix!'
        }
    }
}
def gradlew(String... args) {
    bat ".\\gradlew ${args.join(' ')} -s"
}