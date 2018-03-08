def call() {
	return 1
	
/*	if (result == "SUCCESS") {
		pipeline {
			stage('CICD') {
				sh """
					pwd
					rm -rf /usr/local/cicd/war/home.war
					cp target/*.war /usr/local/cicd/war/.
				"""
			}
		}
	} else {
		echo 'result: >>>>> '+result
	}*/
}