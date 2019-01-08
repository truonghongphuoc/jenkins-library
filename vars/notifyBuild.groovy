#!/usr/bin/env groovy

def call(String buildStatus = 'STARTED', String env, String version = '0.0.0.0', String gitMessage, String frontendApp = 'None') {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

    def color
    def defaultUrl = 'alax.io'
    def frontEndUrl= ''

    if (frontendApp != 'None'){
        switch(env) {
            case dev:
                if (frontendApp == 'publisher') {
                    frontEndUrl = 'https://development-store.' + url
                } else {
                    frontEndUrl = 'https://devlopment-admin.' + url
                }
                break;
            case test:
                if (frontendApp == 'publisher') {
                    frontEndUrl = 'https://test-store.' + url
                } else {
                    frontEndUrl = 'https://test-admin.' + url
                }
                break;
            case staging:
                if (frontendApp == 'publisher') {
                    frontEndUrl = 'https://staging-store.' + url
                } else {
                    frontEndUrl = 'https://staging-admin.' + url
                }
                break;
            case prod:
                if (frontendApp == 'publisher') {
                    frontEndUrl = 'https://store.' + url
                } else {
                    frontEndUrl = 'https://admin.' + url
                }
                break;
        }
    }

    if (buildStatus == 'STARTED') {
        color = '#D4DADF'
    } else if (buildStatus == 'SUCCESS') {
        color = '#00FF00'
    } else {
        color = '#FF0000'
    }
    
    def msg = "${buildStatus}: ${env.JOB_NAME} #${env.BUILD_NUMBER}:\n${env.BUILD_URL}\n* Version:${version}\n* Commit Message:\n${gitMessage}"
    
    slackSend(color: color, message: msg)
}