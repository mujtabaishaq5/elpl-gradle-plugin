# ELPL Android Gradle Plugin

[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fmaven2%2Fio%2Fgithub%2Fmujtabaishaq5%2Felpl%2Fio.github.mujtabaishaq5.elpl.gradle.plugin%2Fmaven-metadata.xml&label=Gradle%20Plugin)](https://plugins.gradle.org/plugin/io.github.mujtabaishaq5.elpl)
[![ELPL Website](https://img.shields.io/badge/ELPL-Official%20Website-blue)](https://elpl-d8625.web.app/)

The **ELPL Android Gradle Plugin** integrates the [ELPL (English Like Programming Language)](https://elpl-d8625.web.app/) compiler into the standard Android build pipeline, allowing developers to write Android application source code in `.elpl` files and compile it as part of their existing Gradle projects.

ELPL brings an English-like, beginner-friendly programming syntax to Android development while supporting interoperability with **Java and Kotlin through the JVM ecosystem**. Developers can introduce ELPL into their projects without abandoning the established Android development toolchain.

A working Android application written entirely in ELPL has been published on Google Play, demonstrating the language's practical application beyond experimental examples.

## Table of Contents

- [ELPL Language and Documentation](#elpl-language-and-documentation)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [How It Works](#how-it-works)
- [Example Project](#example-project)
- [IDE and Developer Tooling](#ide-and-developer-tooling)
- [Release Notes](#release-notes)

## ELPL Language and Documentation

ELPL is a publicly documented programming language with a compiler, an interactive browser-based IDE, downloadable technical documentation, sample programs, and a formal EBNF grammar.

Explore the language and its ecosystem:

- **[Official ELPL Website](https://elpl-d8625.web.app/)** — Explore the language and try ELPL in the browser-based IDE.
- **[Language Documentation and Grammar](https://elpl-d8625.web.app/resources.html)** — Access downloadable Markdown documentation, sample code, and EBNF grammar files.
- **[ELPL Compiler (`elplc`)](https://elpl-d8625.web.app/download.html)** — Learn about the compiler and its available commands.
- **[ELPL Ecosystem](https://elpl-d8625.web.app/ecosystem.html)** — Explore ELPL tooling, platform support, and integrations.

## Features

- **ELPL source compilation:** Compile `.elpl` source files into JVM `.class` files as part of the Android build process.
- **Java interoperability:** Use ELPL alongside Java and interact with the Java-based JVM ecosystem.
- **Kotlin interoperability:** Integrate ELPL into projects that use Kotlin and other JVM-based components.
- **Android ecosystem integration:** Work within existing Android projects using Gradle and the Android Gradle Plugin.
- **Simplified configuration:** Apply the ELPL Gradle plugin through the standard Gradle plugins DSL.
- **Existing Android toolchain:** Continue using Gradle and the Android build system to package your application.

## Requirements

Before using the plugin, ensure your development environment meets these requirements:

- **Gradle:** 8.0 or higher
- **Android Gradle Plugin (AGP):** 8.2.0 or higher
- **Java:** JDK 17 or higher

## Installation

### 1. Apply the plugin

In your Android application module's `build.gradle.kts` file, add the ELPL plugin to the `plugins` block.

```kotlin
plugins {
    id("com.android.application")
    id("io.github.mujtabaishaq5.elpl") version "1.0.14"
}
```

**Always use the latest published version.** Check the automatically updating version badge at the top of this README or visit the [ELPL Android Gradle Plugin page on the Gradle Plugin Portal](https://plugins.gradle.org/plugin/io.github.mujtabaishaq5.elpl).

Replace `1.0.14` with the latest available version when a newer release is published.

### 2. Add your ELPL source files

Place your `.elpl` source files in the source directory expected by the plugin configuration. For the example project, ELPL application sources are located in:

```text
app/
└── src/
    └── elpl/
        └── main/
            └── Main.elpl
```

### 3. Sync and build

Sync your Gradle project and build your Android application using your usual Gradle workflow.

Consult the [official ELPL documentation](https://elpl-d8625.web.app/) for language syntax, compiler usage, and additional examples.

## How It Works

The plugin integrates ELPL compilation into the Android build pipeline:

1. Developers write application source code in `.elpl` files.
2. The ELPL compiler compiles the source files into JVM `.class` files.
3. Gradle and the Android build toolchain process the compiled classes as part of the application build.
4. The Android build system packages the application into an APK or Android App Bundle.

This approach enables developers