package com.bluewhaleyt.codetoolbox.language.java.project

import com.bluewhaleyt.codetoolbox.core.diagnostic.Diagnostor
import com.bluewhaleyt.codetoolbox.language.java.diagnostic.JavaDiagnostor
import com.bluewhaleyt.codetoolbox.language.java.environment.JavaEnvironment
import java.io.File

class JavaProject internal constructor(
    override val dir: File,
    override val rootDir: File
) : JvmProject(dir, rootDir) {

    override val environment by lazy {
        JavaEnvironment.create(this)
    }

    override val diagnostor by lazy {
        JavaDiagnostor(this)
    }

    override val srcDir: File
        get() = File(dir, "src/main/java")

    override fun getSourceFile(fileName: String): File? {
        return sourceFiles.find { fileName == it.name }
    }

    companion object {
        fun create(rootDir: File, name: String) = JavaProject(
            dir = File(rootDir, "java/projects/$name"),
            rootDir = File(rootDir, "java")
        )
    }

}