#!/usr/bin/env groovy

def call(String buildStatus = 'STARTED', String version, String gitMessage) {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

    def color

    if (buildStatus == 'STARTED') {
        color = '#D4DADF'
    } else if (buildStatus == 'SUCCESS') {
        color = '#00FF00'
    } else {
        color = '#FF0000'
    }

    def msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}\n* Version:${version}\n* Commit Message:\n${gitMessage}"

    slackSend(color: color, message: msg)
}