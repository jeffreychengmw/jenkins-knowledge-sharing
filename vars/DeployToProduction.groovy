def call() { 
	timeout(time:5, unit: 'MINUTES') {
		def userAccessToken = input(
			id: 'userAccessToken', message: 'Please input password to proceed',
			parameters: [
				// need to install 'Mask Password' plugin to use the class
				[$class: 'com.michelin.cio.hudson.plugins.passwordparam.PasswordParameterDefinition', name: 'password']
			]
		)
		return userAccessToken
	}
}
