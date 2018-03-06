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
	        }
	        //pipeline的各个阶段场景
	        stages {
	            stage('Build Initialization') {
	                steps {
	                //一些初始化操作
						echo "Initilize Project Build Environment......"
					}
				}
				stage('Fetch Codes from Repo') { 
					steps {
					//一些初始化操作
						script {
							echo "starting fetchCode from Git SCM......"
							// Get some code from a GitHub repository
							//git credentialsId:CRED_ID, url:REPO_URL, branch:params.repoBranch
							checkout scm
						}
					}
				}
	            stage('Unit Test') {
					steps {
						//一些初始化操作
						echo "stage unit test"
					}
	            }
				/*stage('Promote to Production') {
					steps {
						
						echo "Promote to Production Server"
						
						//echo "User Access Token 1: ${userAccessToken1}"
						//echo "User Access Token 2: ${userAccessToken2}"
						//echo "User Access Token 1 Password: ${userAccessToken1['password']}"
						//echo "User Access Token 2 Password: ${userAccessToken2['password']}"
					}
				}*/
	        } // end stages
	        post{
	        	always {
	        		echo "post always"
					echo "${CREDENTIALS}"
					echo "User Password: ${map.userPassword}"
	        	}
	        }
	    }
    }
}

