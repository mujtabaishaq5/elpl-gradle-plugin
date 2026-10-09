# ELPL Android Gradle Plugin

[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fmaven2%2Fio%2Fgithub%2Fmujtabaishaq5%2Felpl%2Fio.github.mujtabaishaq5.elpl.gradle.plugin%2Fmaven-metadata.xml\&label=Gradle%20Plugin)](https://plugins.gradle.org/plugin/io.github.mujtabaishaq5.elpl)

The **ELPL Android Gradle Plugin** integrates the [ELPL (English Like Programming Language)](https://elpl-d8625.web.app/) compiler into the standard Android build pipeline, enabling developers to write Android application source code in `.elpl` files and compile it as part of their existing Gradle projects.

ELPL brings an English-like, beginner-friendly programming syntax to Android development while supporting interoperability with **Java and Kotlin through the JVM ecosystem**. Developers can introduce ELPL into their projects without abandoning the established Android development toolchain.

A working Android application written entirely in ELPL has also been published on Google Play, demonstrating its practical application beyond experimental language examples.

## ELPL Language Documentation

ELPL is a publicly documented programming language with a compiler, an interactive browser-based IDE, downloadable technical documentation, sample programs, and a formal EBNF grammar.

Before getting started with the Gradle plugin, explore these resources:

* **[ELPL Official Website](https://elpl-d8625.web.app/)** — Explore the language and try ELPL in the browser-based IDE.
* **[Language Documentation and Grammar](https://elpl-d8625.web.app/resources.html)** — Access downloadable Markdown documentation, sample code, and EBNF grammar files.
* **[ELPL Compiler (`elplc`)](https://elpl-d8625.web.app/download.html)** — Learn about the compiler and its available commands.
* **[ELPL Ecosystem](https://elpl-d8625.web.app/ecosystem.html)** — Explore ELPL tooling, platform support, and integrations.

## Android Development with ELPL

The plugin brings ELPL compilation into the Android Gradle build process. Developers can maintain `.elpl` source files alongside conventional Android project files and build their applications through Gradle.

Key capabilities include:

* **ELPL source compilation:** Compile `.elpl` files into JVM `.class` files as part of the Android build process.
* **Java interoperability:** Use ELPL alongside Java and integrate with the Java-based JVM ecosystem.
* **Kotlin interoperability:** Integrate ELPL into projects that use Kotlin and other JVM-based components.
* **Android ecosystem integration:** Work within existing Android projects using Gradle and the Android Gradle Plugin.
* **Simplified configuration:** Apply the ELPL Gradle plugin through the standard Gradle plugins DSL.

For a real-world example, see the [ELPL-powered Android application on Google Play](https://play.google.com/store/apps/details?id=com.syedm.testproject).

## Requirements

* **Gradle:** 8.0 or higher
* **Android Gradle Plugin (AGP):** 8.2.0 or higher
* **Java:** JDK 17 or higher

## Installation

Add the plugin to your Android application module's `build.gradle.kts` file using the plugins DSL:

```kotlin
plugins {
    id("com.android.application")
    id("io.github.mujtabaishaq5.elpl") version "1.0.13"
}
```

Sync your Gradle project and follow the [ELPL documentation](https://elpl-d8625.web.app/) to get started with the language and compiler.

## How It Works

The plugin integrates ELPL compilation into the Android build pipeline:

1. Developers write application source code in `.elpl` files.
2. The ELPL compiler compiles the source files into JVM `.class` files.
3. Gradle and the Android build toolchain process the compiled classes as part of the application build.
4. The resulting Android application can be packaged using the standard Android build process.

This approach allows developers to use ELPL for Android development while retaining access to Java, Kotlin, and the wider JVM ecosystem.

## IDE and Developer Tooling

ELPL also provides development resources and editor integrations:

* [Browser-based ELPL IDE](https://elpl-d8625.web.app/)
* [Visual Studio Code extension]([https://marketplace.visualstudio.com/](https://marketplace.visualstudio.com/items?itemName=SyedIshaq.elpl-vscode-extension))
* [JetBrains IDE marketplace]([https://plugins.jetbrains.com/](https://plugins.jetbrains.com/plugin/33250-elpl-support/versions/stable/1123075))

Visit the [official ELPL website](https://elpl-d8625.web.app/) for language resources, documentation, and tooling information.
