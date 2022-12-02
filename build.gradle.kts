plugins {
    kotlin("jvm") version "1.7.22"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }

    build {
        dependsOn("ktlintApplyToIdea")
        dependsOn("addKtlintFormatGitPreCommitHook")
        dependsOn("ktlintKotlinScriptCheck")
        dependsOn("ktlintCheck")
    }
}
