def call(String type, Map map) {
    if (type == "maven") {
	    pipeline {
	        agent any
	        //possible parameter types[booleanParam, choice, credentials, file, text, password, run, string]
	        parameters {
				string(name:'repoHost', defaultValue: "${map.repoHost}", description: 'repository branch')
				string(name:'repoPath', defaultValue: "${map.repoPath}", description: 'repository path')
				string(name:'repoBranch', defaultValue: "${map.repoBranch}", description: 'repository branch')
				choice(name:'repoConnectionType', choices:"ssh\nhttp\nhttps\ngit\nsftp\nftp\nfile", description: 'please choose suitable connection type')
				string(name:'repoConnectionPort', defaultValue: "${map.repoConnectionPort}", description: 'repository connection port')
				string(name:'repoCredentials', defaultValue: "${map.repoCredentials}", description: 'repository credentials')
			}
	        //������������ʼȷ����һ�㲻�����
	        tools {
	            maven "${map.maven}"
	            jdk   "${map.jdk}"
	        }
	        //������������ʼȷ����һ�㲻�����
	        environment{
	            REPO_URL="${map.repoConnectionType}://${map.repoHost}:${map.repoConnectionPort}${map.repoPath}"
	        }
	        options {
	            disableConcurrentBuilds()
	            timeout(time: 10, unit: 'MINUTES')
	            //���ֹ�����������
	            buildDiscarder(logRotator(numToKeepStr: '10'))
	        }
	        //pipeline�ĸ����׶γ���
	        stages {
	            stage('Build Initialization') {
	                steps {
	                //һЩ��ʼ������
	                    echo "${REPO_URL}"
						echo "Repo Host: ${map.repoHost}"
						echo "Repo Host: ${map.repoPath}"
					}
				}
				stage('Fetch Codes from Repo') {
					steps {
					//һЩ��ʼ������
						script {
							echo "starting fetchCode from ${REPO_URL}......"
							// Get some code from a GitHub repository
							//git credentialsId:CRED_ID, url:REPO_URL, branch:params.repoBranch
							checkout scm
						}
					}
				}
	            stage('Unit Test') {
					steps {
						//һЩ��ʼ������
						echo "stage unit test"
					}
	            }
				stage('Promote to Production') {
					steps {
						//һЩ��ʼ������
						echo "Promote to Production Server"
						

						timeout(time:45, unit: 'SECONDS') {
							def userAccessToken = input(
								id: 'promoteToProductionToken', message: 'Please input password to proceed',
								parameters: [
									[$class: 'TextParameterDefinition', name: 'password']
								]
							)
							//echo (userAccessToken['password'])
							//echo (userAccessToken)
							echo "User Access Token: ${userAccessToken}"
							//echo (PASSWORD)
						}
					}
				}
	        } // end stages
	        post{
	        	always {
	        		echo "post always"
	        	}
	        }
	    }
    }
}