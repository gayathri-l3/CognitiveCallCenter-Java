stage 'Build'
node {
    docker.image('springboot-maven3:latest').inside {
        git 'https://github.com/gayathri-l3/spring-boot-logging.git'
        sh 'mvn clean package'
        archive 'target/*.war,target/*.jar'
    }
}

stage 'Deploy'
node {
    docker.image('springboot-maven3:latest').inside {
        //Requires CloudBees OpenShift CLI Plugin to
        //automatically install OC client and log in to the server
        wrap([$class: 'OpenShiftBuildWrapper', 
                url: 'https://osp9ocplb1.cinci.atubsu.com:8443',
                credentialsId: '1014246',
                insecure: true, //Don't check server certificate
                ]) {
                // oc & source2image
                sh """
                oc start-build springdev-build
                """
        }
    }
}

node('maven') {
stage 'buildInDevelopment'
openshiftBuild(buildConfig: 'springdev', showBuildLogs: 'true')
stage 'deployInDevelopment'
openshiftDeploy(deploymentConfig: 'springdev')
openshiftScale(deploymentConfig: 'springdev',replicaCount: '1')
}
