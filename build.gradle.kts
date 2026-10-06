plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.syedm.elpl"
version = "1.0.10"

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("elplAndroidPlugin") {
            id = "com.syedm.elpl.android"
            implementationClass = "com.syedm.elpl.gradle.ElplAndroidPlugin"
        }
    }
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.2.0")
}

// 🔥 Configure publishing to GitHub Packages
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/mujtabaishaq5/elpl-gradle-plugin/")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}