def call(body) {
	
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()

/*	node {
		// Clean workspace before doing anything
		deleteDir()
	
	}*/
	
	/*pipeline {
		agent any
		
		parameters {
			string (name: 'repoUrl', defaultValue: 'project_repoUrl', description: 'Project Repository URL')
			string (name: 'repoBranch', defaultValue: 'project_repoBranch', description: 'Project Repository Branch')
		}

		stage ('Clone') {
			checkout scm
		}
		stage ('Build') {
			sh "echo 'building ${config.projectName} ...'"
		}
		stage ('Tests') {
			parallel 'static': {
				sh "echo 'shell scripts to run static tests...'"
			},
			'unit': {
				sh "echo 'shell scripts to run unit tests...'"
			},
			'integration': {
				sh "echo 'shell scripts to run integration tests...'"
			}
		}
		stage ('Deploy') {
			sh "echo 'deploying to server ${config.serverDomain}...'"
		}

	}*/
}