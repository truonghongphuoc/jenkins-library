#!/usr/bin/env groovy

def call(String buildStatus = 'STARTED', String env, String version = '0.0.0.0', String gitMessage, String frontendApp = 'None') {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

    def color
    def defaultUrl = 'alax.io'
    def frontEndUrl= ''
    def msg

    if (frontendApp != 'None'){
        switch(env) {
            case dev:
                println('Go here')
                if (frontendApp == 'publisher') {
                    frontEndUrl = 'https://development-store.' + url
                } else {
                    frontEndUrl = 'https://devlopment-admin.' + url
                }
            case test:
                if (frontendApp == 'publisher') {
                    frontEndUrl = 'https://test-store.' + url
                } else {
                    frontEndUrl = 'https://test-admin.' + url
                }
            case staging:
                if (frontendApp == 'publisher') {
                    frontEndUrl = 'https://staging-store.' + url
                } else {
                    frontEndUrl = 'https://staging-admin.' + url
                }
            case prod:
                if (frontendApp == 'publisher') {
                    frontEndUrl = 'https://store.' + url
                } else {
                    frontEndUrl = 'https://admin.' + url
                }
        }
    }

    if (buildStatus == 'STARTED') {
        color = '#D4DADF'
        msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}\n* Version:${version}\n* Commit Message:\n${gitMessage}"
    } else if (buildStatus == 'SUCCESS') {
        color = '#00FF00'
    } else {
        color = '#FF0000'
    }
    
    if (frontendApp == 'None'){
        msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}\n* Version:${version}\n* Commit Message:\n${gitMessage}"
    } else {
        msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}\n* Version:${version}\n* Deployed at: ${frontEndUrl}* Commit Message:\n${gitMessage}"
    }
    
    slackSend(color: color, message: msg)
}