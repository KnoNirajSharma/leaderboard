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
        password("env.URL", "credentialsJSON:9e325e6e-70b6-4f36-89ca-c769e8a23101", display = ParameterDisplay.HIDDEN)
        password("rgistrationKey", "credentialsJSON:3069da1f-4bbe-456d-94c3-506f9fd99b13", display = ParameterDisplay.HIDDEN)
        password("env.DRIVER", "credentialsJSON:7a10bff5-a735-41ee-99b9-05ca15fec0e5", display = ParameterDisplay.HIDDEN)
        password("env.PASSWORD", "credentialsJSON:e60519bb-34f8-41f5-a0c6-a15c8923290f", display = ParameterDisplay.HIDDEN)
        password("registrationKey", "credentialsJSON:3069da1f-4bbe-456d-94c3-506f9fd99b13", display = ParameterDisplay.HIDDEN)
        password("env.USERNAME", "credentialsJSON:e60519bb-34f8-41f5-a0c6-a15c8923290f", display = ParameterDisplay.HIDDEN)
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
            param("sbt.args", "clean test")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "coverage-report"
            type = "SBT"
            param("sbt.args", "coverage test coverageReport")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "cpd-report"
            type = "SBT"
            param("sbt.args", "cpd")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "scapegoat-report"
            type = "SBT"
            param("sbt.args", "scapegoat")
            param("teamcity.build.workingDir", "app")
        }
        step {
            name = "scalastyle"
            type = "SBT"
            param("sbt.args", "scalastyle")
            param("teamcity.build.workingDir", "app")
        }
        script {
            name = "scoverage-to -codesquad"
            workingDir = "app"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/9e613b7f3f7061db/app/target/scala-2.12/scoverage-report/scoverage.xml" -F "registrationKey=%rgistrationKey%" https://www.getcodesquad.com/api/add/reports"""
        }
        script {
            name = "cpd-to-codesquad"
            workingDir = "app"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/9e613b7f3f7061db/app/target/scala-2.12/cpd/cpd.xml" -F "registrationKey=%registrationKey%" https://www.getcodesquad.com/api/add/reports"""
        }
        script {
            name = "scapegoat-report-to-codesquad"
            workingDir = "app"
            scriptContent = """curl -X PUT -F "projectName=knoldus-leaderboard" -F "moduleName=leaderboard" -F "organisation=knoldus inc" -F "file=@/opt/buildagent/work/9e613b7f3f7061db/app/target/scala-2.12/scapegoat-report/scapegoat.xml" -F "registrationKey=%registrationKey%" https://www.getcodesquad.com/api/add/reports"""
        }
        step {
            name = "build docker image"
            type = "SBT"
            param("sbt.args", "docker:publishLocal")
            param("teamcity.build.workingDir", "app")
        }
        script {
            name = "login to github package"
            workingDir = "app"
            scriptContent = "docker login docker.pkg.github.com --username sakshigawande12 --password 1f12b95338c889d828e144f5de69e500a151e49f"
        }
        script {
            name = "tag image"
            workingDir = "app"
            scriptContent = "docker knoldus_leaderboard:0.1 docker.pkg.github.com/knoldus/leaderboard/knoldus_leaderboard:0.1"
        }
        script {
            name = "push docker image"
            workingDir = "app"
            scriptContent = "docker push docker.pkg.github.com/knoldus/leaderboard/knoldus_leaderboard:0.1"
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
                    token = "credentialsJSON:91d45ec7-80d0-455a-8a54-d885df206673"
                }
            }
        }
        dockerSupport {
        }
    }
})
