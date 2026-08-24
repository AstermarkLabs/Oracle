pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // hdp-node (dev.astermark:hdp-node) is a separate project (../HDPAndroidNode/hdp-node)
        // published locally via `./gradlew publishToMavenLocal` — see CLAUDE.md.
        mavenLocal()
    }
}

rootProject.name = "Oracle"
include(":app")
