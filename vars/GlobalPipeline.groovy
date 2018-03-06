def call(String type, Map map) {
    if (type == "maven") {
	    pipeline {
	        agent any
	        //possible parameter types[booleanParam, choice, credentials, file, text, password, run, string]
	        /*parameters {
				string(name:'repoCredentials', defaultValue: "${map.repoCredentials}", description: 'repository credentials')
			}*/
	        //������������ʼȷ����һ�㲻�����
	        tools {
	            maven "${map.maven}"
	            jdk   "${map.jdk}"
	        }
	        //������������ʼȷ����һ�㲻�����
	        environment{
				CREDENTIALS = credentials('deployToProductionCredentials_hivesplace')
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
						echo "Initilize Project Build Environment......"
					}
				}
				stage('Fetch Codes from Repo') { 
					steps {
					//һЩ��ʼ������
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
						//һЩ��ʼ������
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

