def call(String result) {
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
}