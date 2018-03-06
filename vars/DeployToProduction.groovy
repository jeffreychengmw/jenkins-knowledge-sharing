def call() { 
	timeout(time:45, unit: 'SECONDS') {
		def userAccessToken = input(
			id: 'userAccessToken', message: 'Please input password to proceed',
			parameters: [
				[$class: 'TextParameterDefinition', name: 'password']
			]
		)
		return userAccessToken
	}
}
