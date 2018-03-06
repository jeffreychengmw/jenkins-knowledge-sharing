def call() { 
	timeout(time:45, unit: 'SECONDS') {
		def userAccessToken = input(
				id: 'userAccessToken', message: 'Please input password to proceed',
				
				)
				//sh """
				//echo ('User Access Token Password: '+userAccessToken['password'])
				//echo ('User Access Token: '+userAccessToken)
				//echo (userAccessToken['password'])
				//echo "Env. Credentials: ${CREDENTIALS}"
				//"""
				//echo (PASSWORD)
	}
}
