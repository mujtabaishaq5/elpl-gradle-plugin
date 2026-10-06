package com.syedm.elpl.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.*

abstract class InjectElplClassesTask : DefaultTask() {
    @get:InputDirectory
    abstract val inputClasses: DirectoryProperty

    @get:OutputDirectory
    abstract val outputClasses: DirectoryProperty

    @TaskAction
    fun execute() {
        val input = inputClasses.get().asFile
        val output = outputClasses.get().asFile
        output.deleteRecursively()
        if (input.exists()) {
            input.copyRecursively(output, overwrite = true)
        }
    }
}