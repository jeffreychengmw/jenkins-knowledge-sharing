def call() { 
	timeout(time:5, unit: 'MINUTES') {
		def userAccessToken = input(
			id: 'userAccessToken', message: 'Please input password to proceed',
			parameters: [
				[$class: 'TextParameterDefinition', name: 'password']
			]
		)
		return userAccessToken
	}
}
