def call() { 
	timeout(time:5, unit: 'MINUTES') {
		def userAccessToken = input(
			id: 'userAccessToken', message: 'Please input password to proceed',
			parameters: [
				[$class: 'com.michelin.cio.hudson.plugins.passwordparam.PasswordParameterDefinition', name: 'password']
			]
		)
		return userAccessToken
	}
}
