import groovy.transform.Field;

// call shared libraries (library name as per Jenkins configuration, normally in the form of git scm/github )
library 'jenkins-shared-library'

@Field def map = [:]
    /*use map or parameters to define project specific variables*/
    map.put('maven','M3')
    map.put('jdk','jdk8')
    map.put('credentialsId','deployToProductionCredentials_hivesplace')
    map.put('cicdPath','/usr/local/cicd/war/.')
    map.put('mavenWar','home-0.1.0.BETA.war')
    map.put('projectWar','home.war')

def deployToProductionStage() {
    // add 1 stage at the end of shared library pipeline template
	stage name: 'Promote to Production Deploy Pipeline', concurrency: 1

    // retrieve and store input value to 'input' variable from DeployToProduction.groovy file
    // easy import DeployToProduction.groovy as a closure object
	def input = DeployToProduction()

    // set inputted password as 'userPassword' variable
	map.put('userPassword',input)
	
	node('master') {
		// compare user inputted password with credentialsId password
		// the other way to validate is to use the 'submitter' parameters of input. which could avoid user input
		withCredentials([usernamePassword(credentialsId: map.credentialsId,
			usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
			script {
				if (map.userPassword == env.PASSWORD) {
					echo 'Password correct'
					sh """
						pwd
						rm -rf ${map.cicdPath}/${map.projectWar}
						cp target/*.war ${map.cicdPath}
						mv ${map.cicdPath}/${map.mavenWar} ${map.cicdPath}/${map.projectWar}
					"""			
				} else {
					echo 'Password not correct'
					currentBuild.result = 'FAILED'
				}
			}
		}
	}
}

// pass project specific configurations and logics to Shared Libraries
GlobalPipeline("maven",map)
deployToProductionStage()