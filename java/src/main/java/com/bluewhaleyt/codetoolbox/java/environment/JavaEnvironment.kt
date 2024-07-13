package com.bluewhaleyt.codetoolbox.java.environment

import com.bluewhaleyt.codetoolbox.java.JavaAnalyzer
import com.bluewhaleyt.codetoolbox.java.project.JavaProject
import com.intellij.core.JavaCoreApplicationEnvironment
import com.intellij.core.JavaCoreProjectEnvironment
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.sun.tools.javac.api.JavacTool
import java.io.File
import java.nio.charset.Charset
import java.util.Locale
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.StandardLocation

class JavaEnvironment internal constructor(
    val project: JavaProject,
    val coreEnvironment: JavaCoreProjectEnvironment
) {

    val args by lazy {
        listOf(
            "-XDcompilePolicy=byfile",
            "-XD-Xprefer=source",
            "-XDide",
            "-XDsuppressAbortOnBadClassFile",
            "-XDshould-stop.at=GENERATE",
            "-XDdiags.formatterOptions=-source",
            "-XDdiags.layout=%L%m|%L%m|%L%m",
            "-XDbreakDocCommentParsingOnError=false",
            "-Xlint:cast",
            "-Xlint:deprecation",
            "-Xlint:empty",
            "-Xlint:fallthrough",
            "-Xlint:finally",
            "-Xlint:path",
            "-Xlint:unchecked",
            "-Xlint:varargs",
            "-Xlint:static",
            "-proc:none"
        )
    }

    var diagnosticCollector = DiagnosticCollector<JavaFileObject>()

    internal val tool by lazy { JavacTool.create() }

    internal val standardFileManager by lazy {
        tool.getStandardFileManager(
            diagnosticCollector, Locale.getDefault(), Charset.defaultCharset()
        )
    }

    init {
        standardFileManager.setLocation(
            StandardLocation.PLATFORM_CLASS_PATH,
            project.classpathDir.walk().toList()
        )
        if (!project.binDir.exists()) {
            project.binDir.mkdirs()
        }
        standardFileManager.setLocation(StandardLocation.CLASS_OUTPUT, listOf(project.binDir))
    }

    internal fun getClasspath(): List<File> {
        val classpath = mutableListOf<File>()
        classpath.add(File(project.binDir, "classes"))
        project.libDir.walk().forEach {
            if (it.extension == "jar") {
                classpath.add(it)
            }
        }
        project.binDir
            .resolve("classes")
            .walk()
            .filter { it.extension == "class" }
            .forEach {
                if (JavaAnalyzer.Cache.getCache(it) != null && JavaAnalyzer.Cache.getCache(it)!!.lastModified == it.lastModified()) {
                    classpath.add(it)
                }
            }
        return classpath
    }

    internal fun getSourceFiles(): List<JavaFileObject> {
        val sourceFiles = mutableListOf<JavaFileObject>()
        project.srcDir.walk().forEach {
            if (it.extension == "java") {
                val cache = JavaAnalyzer.Cache.getCache(it)
                if (cache == null || cache.lastModified < it.lastModified()) {
                    sourceFiles.add(JavaAnalyzer.Cache.saveCache(it))
                }
            }
        }
        return sourceFiles
    }

    fun createJavaFile(clazz: TypeSpec, packageName: String = ""): JavaFile =
        JavaFile.builder(packageName, clazz)
            .indent("\t")
            .skipJavaLangImports(true)
            .build()

    companion object {
        internal fun create(project: JavaProject) = JavaEnvironment(
            coreEnvironment = JavaCoreProjectEnvironment({}, JavaCoreApplicationEnvironment {}),
            project = project
        )
    }

}