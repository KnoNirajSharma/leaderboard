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
        param("env.URL", "jdbc:h2:mem:test;MODE=Oracle;")
        param("env.DRIVER", "org.h2.Driver")
        param("env.PASSWORD", "")
        param("env.USERNAME", "")
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
            name = "testing"
            type = "SBT"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("sbt.args", "clean test")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "cpd-report"
            type = "SBT"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("sbt.args", "cpd")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "coverage-report"
            type = "SBT"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("sbt.args", "coverageReport")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "scalastyle"
            type = "SBT"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
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
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/bcd363be9c5663b6/app/target/scalastyle-result.xml" -F "registrationKey=2159f15a-6e52-438a-a1a3-4b0e77b30a43" https://www.getcodesquad.com/api/add/reports"""
        }
        script {
            name = "cpd-to-codesquad"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/bcd363be9c5663b6/app/target/scala-2.12/cpd/cpd.xml" -F "registrationKey=2159f15a-6e52-438a-a1a3-4b0e77b30a43" https://www.getcodesquad.com/api/add/reports"""
        }
        script {
            name = "scapegoat-report-to-codesquad"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/bcd363be9c5663b6/app/target/scala-2.12/scapegoat-report/scapegoat.xml" -F "registrationKey=2159f15a-6e52-438a-a1a3-4b0e77b30a43" https://www.getcodesquad.com/api/add/reports"""
        }
        script {
            name = "build docker image"
            type = "SBT"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            workingDir = "app"
            scriptContent = "sbt docker:publishLocal"
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
