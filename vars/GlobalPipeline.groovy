#!groovy
/*def call(String type, Map map) {*/
def call()
    if (type == "maven") {
	    pipeline {
	        agent any
	        //����������,Ŀǰֻ֧��[booleanParam, choice, credentials, file, text, password, run, string]�⼸�ֲ������ͣ������߼����������ͻ���ȴ�����֧��
	        parameters {
	        //�̶���������pipeline����
	        choice(name:'scene',choices:"scene1:������ˮ��\nscene2:������\nscene3:���Բ���", description: '����ѡ��Ĭ������������ˮ�ߣ����ֻ�������Բ��ѡ������飬���ֻ�����������ѡ����Բ���')
	        //repoBranch���������滻��git parameter���������ֹ�����,JENKINS-46451
	        string(name:'repoBranch', defaultValue: "${map.repoBranch}", description: 'git��֧����')
	        //��������ز�����������Ϸ�ʽ��������ѡ��
	        choice(name: 'server',choices:"${map.server}", description: '���Է������б�ѡ��')
	        string(name:'dubboPort', defaultValue: "${map.dubboPort}", description: '���Է�������dubbo����˿�')
	        //��Ԫ���Դ��븲����Ҫ�󣬸���Ŀ��Ҫ���������
	        string(name:'lineCoverage', defaultValue: "${map.lineCoverage}", description: '��Ԫ���Դ��븲����Ҫ��(%)��С�ڴ�ֵpipeline����ʧ�ܣ�')
	        //����ѡ��pipelie��ɺ���ʼ�֪ͨ������Ա��������
	        booleanParam(name: 'isCommitQA', defaultValue: false, description: '�Ƿ���pipeline��ɺ��ʼ�֪ͨ������Ա�����˹�����')
	        }
	        //������������ʼȷ����һ�㲻�����
	        tools {
	            maven "${map.maven}"
	            jdk   "${map.jdk}"
	        }
	        //������������ʼȷ����һ�㲻�����
	        environment{
	            REPO_URL="${map.REPO_URL}"
	            //git����ȫϵͳֻ���˺ţ������޸�
	            CRED_ID="${map.CRED_ID}"
	            //pom.xml�����·��
	            POM_PATH="${map.POM_PATH}"
	            //����war�������·��
	            WAR_PATH="${map.WAR_PATH}"
	            //������Ա�����ַ
	            QA_EMAIL="${map.QA_EMAIL}"
	            //�ӿڲ���job����
	            ITEST_JOBNAME="${map.ITEST_JOBNAME}"
	        }
	
	        options {
	            disableConcurrentBuilds()
	            timeout(time: 1, unit: 'HOURS')
	            //���ֹ�����������
	            buildDiscarder(logRotator(numToKeepStr: '10'))
	        }
	       post{
		   		always {
					   echo "post always"
				   }
	       }
	        //pipeline�ĸ����׶γ���
	        stages {
	            stage('retrieve project code') {
	                steps {
	                //һЩ��ʼ������
	                    script {
	                    //����param.server�ָ��ȡ����
	                    def split=params.server.split(",")
	                    serverIP=split[0]
	                    jettyPort=split[1]
	                    serverName=split[2]
	                    serverPasswd=split[3]
	                    //����ѡ��
	                    println params.scene
	                    //��Ԫ�������г���
	                    isUT=params.scene.contains('scene1:������ˮ��') || params.scene.contains('scene2:������')
	                    println "isUT="+isUT
	                    //��̬���������г���
	                    isCA=params.scene.contains('scene1:������ˮ��') || params.scene.contains('scene2:������')
	                    println "isCA="+isCA
	                    //������Ի������г���
	                    isDP=params.scene.contains('scene1:������ˮ��') || params.scene.contains('scene3:���Բ���')
	                    println "isDP="+isDP
	                    //�������ⰲȫ�Լ��
	                    isDC=params.scene.contains('scene1:������ˮ��')
	                    println "isDC="+isDC
	                    //�ӿڲ������г���
	                    isIT=params.scene.contains('scene1:������ˮ��')
	                    println "isIT="+isIT
	                    try{
	                        wrap([$class: 'BuildUser']){
	                        userEmail="${BUILD_USER_EMAIL},${QA_EMAIL}"
	                        user="${BUILD_USER_ID}"
	                        }
	                   }catch(exc){
	                        userEmail="${QA_EMAIL}"
	                        user="system"
	                    }
	                  echo "starting fetchCode from ${REPO_URL}......"
	                  // Get some code from a GitHub repository
	                  git credentialsId:CRED_ID, url:REPO_URL, branch:params.repoBranch
	                 }
	                }
	            }
	            stage('unit test') {
					steps {
						//һЩ��ʼ������
						echo "stage unit test"
					}
	            }
	        }
	    }
    }
}