import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
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
        param("env.USERNAME", "")
        param("env.PASSWORD", "")
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
            name = "packagining"
            type = "SBT"
            param("sbt.args", "package")
            param("teamcity.build.workingDir", "app")
        }
        step{
            name = "coverage test"
            type = "SBT"
            param("sbt.args", "clean coverage test")
            param("teamcity.build.workingDir", "app")
        }
        step{
            name = "cpd-report"
            type = "SBT"
            param("sbt.args", "cpd")
            param("teamcity.build.workingDir", "app")
        }
        step{
            name = "scalastyle-report"
            type = "SBT"
            param("sbt.args", "scalastyle")
            param("teamcity.build.workingDir", "app")
        }
        step{
            name = "scapegoat-report"
            type = "SBT"
            param("sbt.args", "scapegoat")
            param("teamcity.build.workingDir", "app")
        }
        step{
            name = "build docker image"
            type = "SBT"
            param("sbt.args", "docker:publishLocal")
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
                    token = "credentialsJSON:425701aa-5ad1-4fe6-bdc0-a3a00d1c4881"
                }
            }
        }
    }
})
