def call(String type, Map map) {
    if (type == "maven") {
	    pipeline {
	        agent any
	        //possible parameter types[booleanParam, choice, credentials, file, text, password, run, string]
	        /*parameters {
				string(name:'repoCredentials', defaultValue: "${map.repoCredentials}", description: 'repository credentials')
			}*/
	        // environment variables, settings as per Jenkins configuration
	        tools {
	            maven "${map.maven}"
	            jdk   "${map.jdk}"
	        }
	        //常量参数，初始确定后一般不需更改
	        environment{
				CREDENTIALS = credentials('deployToProductionCredentials_hivesplace')
			}
	        options {
	            disableConcurrentBuilds()
	            timeout(time: 10, unit: 'MINUTES')
	            //maximum build history. discard old builds if exceeded
	            buildDiscarder(logRotator(numToKeepStr: '10'))
				// prevent double source checkout
				skipDefaultCheckout()
	        }
	        //pipeline stages
	        stages {
	            stage('Build Initialization') {
	                steps {
						echo "Initilize Project Build Environment......"
					}
				}
				stage('Build Project') {
					steps {
						script {
							echo "building project......"
							sh """
								mvn clean package
							"""
						}
					}
				}
	            stage('Unit Test') {
					steps {
						echo "stage unit test"
						sh 'mvn test'
					}
	            }
	        } // end stages
	    }
    }
}

