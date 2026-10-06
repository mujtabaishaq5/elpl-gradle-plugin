package com.syedm.elpl.gradle

import com.android.build.api.artifact.ScopedArtifact
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.ScopedArtifacts
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.attributes.Attribute
import org.gradle.kotlin.dsl.*
import java.util.Properties

class ElplAndroidPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        project.pluginManager.withPlugin("com.android.application") {

            val androidComponents = project.extensions.getByType(ApplicationAndroidComponentsExtension::class.java)
            androidComponents.onVariants(androidComponents.selector().all()) { variant ->
                val capitalizedVariantName = variant.name.replaceFirstChar { it.uppercase() }

                val variantOutputDir = project.layout.buildDirectory.dir("elpl-classes/${variant.name}")

                val compileElpl = project.tasks.register<CompileElplTask>("compile${capitalizedVariantName}Elpl") {
                    group = "elpl"
                    description = "Compiles ELPL source to bytecode with Kotlin interop for ${variant.name}"

                    sourceDir.set(project.file("src/main/elpl"))
                    outputDir.set(variantOutputDir)
                    binExtractionDir.set(project.layout.buildDirectory.dir("elpl-compiler-bin"))
                    projectDir.set(project.projectDir)

                    val androidExt = project.extensions.findByName("android")
                    val dynamicCompileSdk = try {
                        val method = androidExt?.javaClass?.methods?.firstOrNull {
                            it.name == "getCompileSdk" || it.name == "getCompileSdkVersion"
                        }
                        method?.invoke(androidExt)?.toString()?.removePrefix("android-")
                    } catch (e: Exception) {
                        null
                    } ?: "36"

                    compileSdk.set(dynamicCompileSdk)

                    val localProps = project.rootProject.file("local.properties")
                    val sdkDir = if (localProps.exists()) {
                        val props = Properties()
                        localProps.inputStream().use { props.load(it) }
                        props.getProperty("sdk.dir") ?: ""
                    } else {
                        System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT") ?: ""
                    }
                    androidSdkDir.set(sdkDir)

                    // Replicate custom.kt artifactView configuration exactly
                    val runtimeConfig = project.configurations.findByName("${variant.name}RuntimeClasspath")
                    if (runtimeConfig != null && runtimeConfig.isCanBeResolved) {
                        val jarArtifacts = runtimeConfig.incoming.artifactView {
                            attributes {
                                attribute(Attribute.of("artifactType", String::class.java), "jar")
                            }
                        }.files
                        dependencyClasspath.from(jarArtifacts)
                    }

                    val kotlinTaskName = "compile${capitalizedVariantName}Kotlin"
                    dependsOn(kotlinTaskName)
                }

                val injectElplClasses = project.tasks.register<InjectElplClassesTask>("inject${capitalizedVariantName}ElplClasses") {
                    dependsOn(compileElpl)
                    inputClasses.set(variantOutputDir)
                }

                variant.artifacts
                    .forScope(ScopedArtifacts.Scope.PROJECT)
                    .use(injectElplClasses)
                    .toAppend(
                        ScopedArtifact.CLASSES,
                        InjectElplClassesTask::outputClasses
                    )
            }
        }
    }
}