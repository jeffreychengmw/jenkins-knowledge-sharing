def call(String result) {
		stage('CICD') {
			sh """
				pwd
			"""
			echo "stage CICD >>>>>>>>>>>>>>>>>>>>>>>>"
		}
	
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