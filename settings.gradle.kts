pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        //for third party library
        maven { url = uri ("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {

            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "Flatmate Finder"
include(":app")
 