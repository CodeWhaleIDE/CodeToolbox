package com.bluewhaleyt.codetoolbox.language.java.project

import com.bluewhaleyt.codetoolbox.core.project.Project
import java.io.File

abstract class JvmProject(
    override val dir: File,
    open val rootDir: File
) : Project {

    abstract val environment: Any

    abstract val srcDir: File

    abstract fun getSourceFile(fileName: String): File?

    val buildDir
        get() = File(dir, "build")

    val cacheDir
        get() = File(buildDir, "cache")

    val binDir
        get() = File(buildDir, "bin")

    val libDir
        get() = File(dir, "libs")

    val classpathDir
        get() = rootDir.resolve("classpath")

    val classpath: List<File>
        get() {
            val classpath = mutableListOf(File(binDir, "classes"))
            if (libDir.exists() && libDir.isDirectory) {
                classpath += libDir.walk().filter { it.extension == "jar" }.toList()
            }
            return classpath
        }

    val systemClasspath
        get() = classpathDir.listFiles()?.toList() ?: emptyList()

    val classesFiles by lazy {
        rootDir.walk()
            .filter { it.extension == "class" }
            .map { it.toPath() }
            .toList()
    }

    val sourceFiles by lazy {
        srcDir.walkTopDown()
            .filter { it.isFile }
            .toList()
    }

}