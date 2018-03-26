def call(String type, Map map) {
    if (type == "maven") {
	    pipeline {
	        agent any
	        //possible parameter types[booleanParam, choice, credentials, file, text, password, run, string]
	        /*parameters {
				string(name:'repoCredentials', defaultValue: "${map.repoCredentials}", description: 'repository credentials')
			}*/
	        //环境变量，初始确定后一般不需更改
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
	            //保持构建的最大个数
	            buildDiscarder(logRotator(numToKeepStr: '10'))
				skipDefaultCheckout()
	        }
	        //pipeline的各个阶段场景
	        stages {
	            stage('Build Initialization') {
	                steps {
	                //一些初始化操作
						echo "Initilize Project Build Environment......"
					}
				}
				stage('Build Project') {
					steps {
					//一些初始化操作
						script {
							echo "building project......"
							// Get some code from a GitHub repository
							//git credentialsId:CRED_ID, url:REPO_URL, branch:params.repoBranch
							sh """
								mvn clean package
							"""
						}
					}
				}
	            stage('Unit Test') {
					steps {
						//一些初始化操作
						echo "stage unit test"
						sh 'mvn test'
					}
	            }
	        } // end stages
	    }
    }
}

