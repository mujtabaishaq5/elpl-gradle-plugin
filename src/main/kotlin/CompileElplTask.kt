package com.syedm.elpl.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations
import javax.inject.Inject
import java.io.File
import java.net.URL

abstract class CompileElplTask @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {

    @get:InputDirectory
    abstract val sourceDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:Internal
    abstract val binExtractionDir: DirectoryProperty

    @get:Internal
    abstract val projectDir: DirectoryProperty

    @get:Input
    abstract val compileSdk: Property<String>

    @get:Input
    abstract val androidSdkDir: Property<String>

    @get:InputFiles
    abstract val dependencyClasspath: ConfigurableFileCollection

    @TaskAction
    fun execute() {
        val outDir = outputDir.get().asFile
        outDir.deleteRecursively()
        outDir.mkdirs()

        val binDir = binExtractionDir.get().asFile
        binDir.mkdirs()

        // 🔥 Store the compiler globally in the user's home directory so it never gets wiped and is shared across all projects
        val userHome = File(System.getProperty("user.home"))
        val globalCacheDir = File(userHome, ".elpl/cache")
        if (!globalCacheDir.exists()) {
            globalCacheDir.mkdirs()
        }

        val compilerJarFile = File(globalCacheDir, "elpl-compiler.jar")

// 🔥 Auto-download the compiler jar from GitHub Releases only if it doesn't exist globally yet
        if (!compilerJarFile.exists()) {
            println("=== ELPL: Downloading 105MB compiler binary from GitHub Releases (one-time global setup) ===")
            val downloadUrl = URL("https://github.com/mujtabaishaq5/elpl-gradle-plugin/releases/download/v1.0.10/elpl-compiler.jar")
            downloadUrl.openStream().use { input ->
                compilerJarFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            println("=== ELPL: Global compiler binary cached successfully ===")
        }

        val sdkDir = androidSdkDir.get()
        val sdkVersion = compileSdk.get()

        var androidJarFile = File(sdkDir, "platforms/android-$sdkVersion/android.jar")
        if (!androidJarFile.exists()) {
            val platformsDir = File(sdkDir, "platforms")
            if (platformsDir.exists() && platformsDir.isDirectory) {
                val availableJar = platformsDir.listFiles()
                    ?.filter { it.isDirectory && it.name.startsWith("android-") }
                    ?.map { File(it, "android.jar") }
                    ?.firstOrNull { it.exists() }

                if (availableJar != null) {
                    androidJarFile = availableJar
                }
            }
        }

        if (!androidJarFile.exists()) {
            throw RuntimeException("CRITICAL: android.jar could not be found in '$sdkDir/platforms'. Please install an Android SDK Platform.")
        }

        val androidJar = androidJarFile.absolutePath
        val compilerJar = compilerJarFile.absolutePath

        val kotlinHelpersJarFile = File(projectDir.get().asFile, "libs/kotlin-helpers.jar")
        val kotlinHelpersJar = kotlinHelpersJarFile.absolutePath

        val depPath = dependencyClasspath.files
            .filter { it.name != "kotlin-helpers.jar" }
            .joinToString(File.pathSeparator) { it.absolutePath }

        val fullClasspath = listOf(androidJar, kotlinHelpersJar, depPath)
            .filter { it.isNotEmpty() }
            .joinToString(File.pathSeparator)

        val jvmClassPath = "$compilerJar${File.pathSeparator}$fullClasspath"

        execOperations.exec {
            workingDir = projectDir.get().asFile

            commandLine(
                "java", "-cp", jvmClassPath, "syed.Main",
                "build",
                "--classpath", fullClasspath,
                "-o", outDir.absolutePath,
                sourceDir.get().asFile.absolutePath
            )

            standardOutput = System.out
            errorOutput = System.err
        }
    }
}

/*package com.syedm.elpl.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations
import javax.inject.Inject
import java.io.File

abstract class CompileElplTask @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {

    @get:InputDirectory
    abstract val sourceDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:Internal
    abstract val binExtractionDir: DirectoryProperty

    @get:Internal
    abstract val projectDir: DirectoryProperty

    @get:Input
    abstract val compileSdk: Property<String>

    @get:Input
    abstract val androidSdkDir: Property<String>

    @get:InputFiles
    abstract val dependencyClasspath: ConfigurableFileCollection

    @TaskAction
    fun execute() {
        val outDir = outputDir.get().asFile
        outDir.deleteRecursively()
        outDir.mkdirs()

        val binDir = binExtractionDir.get().asFile
        binDir.mkdirs()

        val compilerJarFile = File(binDir, "elpl-compiler.jar")
        this::class.java.getResourceAsStream("/elpl-compiler.jar")?.use { input ->
            compilerJarFile.outputStream().use { input.copyTo(it) }
        } ?: throw RuntimeException("elpl-compiler.jar not found inside the plugin!")

        val sdkDir = androidSdkDir.get()
        val sdkVersion = compileSdk.get()

        // 🔥 Smart Fallback: Check if the exact requested compileSdk version exists,
        // otherwise scan platforms/ and find whichever android.jar is available locally.
        var androidJarFile = File(sdkDir, "platforms/android-$sdkVersion/android.jar")
        if (!androidJarFile.exists()) {
            val platformsDir = File(sdkDir, "platforms")
            if (platformsDir.exists() && platformsDir.isDirectory) {
                val availableJar = platformsDir.listFiles()
                    ?.filter { it.isDirectory && it.name.startsWith("android-") }
                    ?.map { File(it, "android.jar") }
                    ?.firstOrNull { it.exists() }

                if (availableJar != null) {
                    androidJarFile = availableJar
                }
            }
        }

        if (!androidJarFile.exists()) {
            throw RuntimeException("CRITICAL: android.jar could not be found in '$sdkDir/platforms'. Please ensure at least one Android SDK Platform is installed in Android Studio.")
        }

        val androidJar = androidJarFile.absolutePath
        val compilerJar = compilerJarFile.absolutePath

        val kotlinHelpersJarFile = File(projectDir.get().asFile, "libs/kotlin-helpers.jar")
        val kotlinHelpersJar = kotlinHelpersJarFile.absolutePath

        val depPath = dependencyClasspath.files
            .filter { it.name != "kotlin-helpers.jar" }
            .joinToString(File.pathSeparator) { it.absolutePath }

        val fullClasspath = listOf(androidJar, kotlinHelpersJar, depPath)
            .filter { it.isNotEmpty() }
            .joinToString(File.pathSeparator)

        val jvmClassPath = "$compilerJar${File.pathSeparator}$fullClasspath"

        execOperations.exec {
            workingDir = projectDir.get().asFile

            commandLine(
                "java", "-cp", jvmClassPath, "syed.Main",
                "build",
                "--classpath", fullClasspath,
                "-o", outDir.absolutePath,
                sourceDir.get().asFile.absolutePath
            )

            standardOutput = System.out
            errorOutput = System.err
        }
    }
}
*/