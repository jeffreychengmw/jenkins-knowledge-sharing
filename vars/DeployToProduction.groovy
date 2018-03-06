def call() { 
	environment{
		CREDENTIALS = credentials('deployToProductionCredentials_hivesplace')
	}
	timeout(time:45, unit: 'SECONDS') {
		def userAccessToken = input(
			id: 'userAccessToken', message: 'Please input password to proceed',
		)
		echo "${CREDENTIALS}"
	}
}
