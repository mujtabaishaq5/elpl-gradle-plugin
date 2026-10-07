# ELPL Android Gradle Plugin

[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fmaven2%2Fio%2Fgithub%2Fmujtabaishaq5%2Felpl%2Fio.github.mujtabaishaq5.elpl.gradle.plugin%2Fmaven-metadata.xml&label=Gradle%20Plugin)](https://plugins.gradle.org/plugin/io.github.mujtabaishaq5.elpl)

The **ELPL Android Gradle Plugin** integrates the ELPL programming language compiler directly into your Android project build pipeline. It allows you to seamlessly compile and bundle ELPL source files into your Android application builds.

---

## Requirements

* **Gradle**: 8.0 or higher
* **Android Gradle Plugin (AGP)**: 8.2.0 or higher
* **Java**: JDK 17 or higher

---

## Installation

Add the plugin to your Android application module's `build.gradle.kts` file using the plugins DSL:

```kotlin
plugins {
    id("com.android.application")
    id("io.github.mujtabaishaq5.elpl") version "1.0.13"
}