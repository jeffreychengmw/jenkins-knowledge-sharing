def call(String type, Map map) {
    if (type == "maven") {
	    pipeline {
	        agent any
	        //possible parameter types[booleanParam, choice, credentials, file, text, password, run, string]
	        parameters {
				string(name:'repoHost', defaultValue: "${map.repoHost}", description: 'repository branch')
				string(name:'repoPath', defaultValue: "${map.repoPath}", description: 'repository path')
				string(name:'repoBranch', defaultValue: "${map.repoBranch}", description: 'repository branch')
				choice(name:'repoConnectionType', choices:"${map.repoConnectionType}", description: 'please choose suitable connection type')
				string(name:'repoConnectionPort', defaultValue: "${map.repoConnectionPort}", description: 'repository connection port')
				string(name:'repoCredentials', defaultValue: "${map.repoCredentials}", description: 'repository credentials')
			}
	        //环境变量，初始确定后一般不需更改
	        tools {
	            maven "${map.maven}"
	            jdk   "${map.jdk}"
	        }
	        //常量参数，初始确定后一般不需更改
	        environment{
	            REPO_URL=${map.repoConnectionType}+"://"+${map.repoHost}+":"+${map.repoConnectionPort}+"/"+${map.repoPath}
	        }
	
	        options {
	            disableConcurrentBuilds()
	            timeout(time: 10, unit: 'MINUTES')
	            //保持构建的最大个数
	            buildDiscarder(logRotator(numToKeepStr: '10'))
	        }
	       post{
		   		always {
					   echo "post always"
				   }
	       }
	        //pipeline的各个阶段场景
	        stages {
	            stage('Build Initialization') {
	                steps {
	                //一些初始化操作
	                    echo "${REPO_URL}"
						echo "Repo Host: ${map.repoHost}"
						echo "Repo Host: ${map.repoPath}"
					}
				}
				stage('Fetch Codes from Repo') {
					steps {
					//一些初始化操作
						script {
							echo "starting fetchCode from ${REPO_URL}......"
							// Get some code from a GitHub repository
							//git credentialsId:CRED_ID, url:REPO_URL, branch:params.repoBranch
							checkout scm
						}
					}
				}
	            stage('unit test') {
					steps {
						//一些初始化操作
						echo "stage unit test"
					}
	            }
	        }
	    }
    }
}