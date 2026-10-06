plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "2.2.1"
}

group = "com.syedm.elpl"
version = "1.0.13"

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    // Required metadata for the Portal
    website.set("https://github.com/mujtabaishaq5/elpl-gradle-plugin")
    vcsUrl.set("https://github.com/mujtabaishaq5/elpl-gradle-plugin.git")

    plugins {
        create("elplAndroidPlugin") {
            id = "com.syedm.elpl.android"
            implementationClass = "com.syedm.elpl.gradle.ElplAndroidPlugin"
            displayName = "ELPL Android Plugin"
            description = "Embeds and runs the ELPL compiler natively inside Android builds"
            tags.set(listOf("elpl", "compiler", "android", "language"))
        }
    }
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.2.0")
}