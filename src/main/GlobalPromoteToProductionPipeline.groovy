def call(String result) {
	echo 'result is : >>>>>>>>>>>>> '+result
	pipeline {
		stages {
			stage('CICD') {
				sh """
				pwd
				rm -rf /usr/local/cicd/war/home.war
				cp target/*.war /usr/local/cicd/war/.
				"""
			}
		}
	}
	return "GlobalPromoteToProductionPipeline"
}