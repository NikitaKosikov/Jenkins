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
        tomcatWeb = 'C:\\Users\\Administrator\\Downloads\\apache-tomcat-9.0.54-windows-x64\\apache-tomcat-9.0.54\\webapps'
        tomcatBin = 'C:\\Users\\Administrator\\Downloads\\apache-tomcat-9.0.54-windows-x64\\apache-tomcat-9.0.54\\bin'
         } 
            steps {
                bat "copy /Y web\\build\\libs\\Gift-Certificate.war \"${tomcatWeb}\\Gift-Certificate.war\""

                sleep(time:5,unit:"SECONDS")
		        bat "${tomcatBin}\\startup.bat"
 		        sleep(time:200,unit:"SECONDS")
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