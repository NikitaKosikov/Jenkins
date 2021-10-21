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
        environment{
        tomcatWeb = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps'
        tomcatBin = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\bin'
         }
       
            steps {
                bat "copy web\\build\\libs\\Gift-Certificate.war ${tomcatWeb}"

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