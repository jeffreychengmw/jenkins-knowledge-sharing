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
				deleteDir()
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
							//checkout scm
						}
					}
				}
				stage('Build Project') {
					steps {
					//һЩ��ʼ������
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
						//һЩ��ʼ������
						echo "stage unit test"
						sh 'mvn test'
					}
	            }
	        } // end stages
/*	        post{
	        	always {
	        		echo "post always"
					echo "${CREDENTIALS}"
	        	}
	        }*/
	    }
    }
}

