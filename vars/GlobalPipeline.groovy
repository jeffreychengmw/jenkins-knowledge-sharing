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
				skipDefaultCheckout()
	        }
	        //pipeline�ĸ����׶γ���
	        stages {
	            stage('Build Initialization') {
	                steps {
	                //һЩ��ʼ������
						echo "Initilize Project Build Environment......"
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
	    }
    }
}

