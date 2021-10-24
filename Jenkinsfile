pipeline {
    agent any

    tools{
	gradle "Gradle"
    }	

    stages {
          
        stage('Compile') {
            steps {
                gradlew('clean', 'build')
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

	stage('Code Analysis') {
	     environment {
        		scannerHome = tool 'SonarQubeScanner'
    		}
  		 steps {
      			  withSonarQubeEnv('sonarqube') {
          		  bat ".\\gradlew sonarqube -D \"sonar.projectKey=Gift-Certificate\" -D \"sonar.host.url=http://localhost:9000\" -D \"sonar.login=7f2fdb5ba5740c945503951950fc3f7bed8e2a04\""
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
		        bat "${tomcatBin}\\startup.bat"

            }
        }
    }
}
def gradlew(String... args) {
    bat ".\\gradlew ${args.join(' ')} -s"
}