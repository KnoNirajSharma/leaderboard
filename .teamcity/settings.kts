import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.1"

project {

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    params {
        password("env.URL", "credentialsJSON:70a3dfeb-6cff-48f6-857e-843bf160b113", display = ParameterDisplay.HIDDEN)
        password("env.DRIVER", "credentialsJSON:b45a8f24-3283-4596-8648-fab11a3314f8", display = ParameterDisplay.HIDDEN)
        password("env.PASSWORD", "credentialsJSON:c3ac330a-ef9e-40b3-af36-16407dddf5fc", display = ParameterDisplay.HIDDEN)
        password("registrationKey", "credentialsJSON:da45fe29-9ef7-4121-afda-9e4ed64e3bff", display = ParameterDisplay.HIDDEN)
        password("env.USERNAME", "credentialsJSON:c3ac330a-ef9e-40b3-af36-16407dddf5fc", display = ParameterDisplay.HIDDEN)
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        step {
            name = "compilation"
            type = "SBT"
            param("sbt.args", "clean compile")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "cpd-report"
            type = "SBT"
            param("sbt.args", "cpd")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "coverage-report"
            type = "SBT"
            param("sbt.args", "coverage test coverageReport")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "scalastyle"
            type = "SBT"
            param("sbt.args", "scalastyle")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "scapegoat-report"
            type = "SBT"
            param("sbt.args", "scapegoat")
            param("teamcity.build.workingDir", "app")
        }
        script {
            name = "scalastyle-to -codesquad"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/bcd363be9c5663b6/app/target/scalastyle-result.xml" -F "registrationKey=%registrationKey%" https://www.getcodesquad.com/api/add/reports"""
        }
        script {
            name = "cpd-to-codesquad"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/bcd363be9c5663b6/app/target/scala-2.12/cpd/cpd.xml" -F "registrationKey=%registrationKey%" https://www.getcodesquad.com/api/add/reports"""
        }
        script {
            name = "scapegoat-report-to-codesquad"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/bcd363be9c5663b6/app/target/scala-2.12/scapegoat-report/scapegoat.xml" -F "registrationKey=%registrationKey%" https://www.getcodesquad.com/api/add/reports"""
        }
        script {
            name = "scoverage-report-to-codesquad"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/bcd363be9c5663b6/app/target/scala-2.12/scoverage-report/scoverage.xml" -F "registrationKey=%registrationKey%" https://www.getcodesquad.com/api/add/reports"""
        }
        step {
            name = "build docker image"
            type = "SBT"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("script.content", "sbt docker:publishLocal")
            param("teamcity.build.workingDir", "app")
            param("use.custom.script", "true")
        }
        step {
            name = "testing"
            type = "SBT"
            param("sbt.args", "test")
            param("teamcity.build.workingDir", "app")
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        commitStatusPublisher {
            vcsRootExtId = "${DslContext.settingsRoot.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:221b3f23-2468-4806-ac16-8931670bfbb7"
                }
            }
        }
        dockerSupport {
            cleanupPushedImages = true
        }
    }
})
