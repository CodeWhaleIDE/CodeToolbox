package com.bluewhaleyt.codetoolbox.language.java.diagnostic

import com.bluewhaleyt.codetoolbox.core.diagnostic.DiagnosticIssue
import com.bluewhaleyt.codetoolbox.core.diagnostic.Diagnostor
import com.bluewhaleyt.codetoolbox.language.java.project.JavaProject
import com.sun.tools.javac.api.JavacTool
import java.io.File
import java.nio.charset.Charset
import java.util.Locale
import javax.tools.Diagnostic
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.SimpleJavaFileObject
import javax.tools.StandardLocation

class JavaDiagnostor(
    override val project: JavaProject
) : Diagnostor {

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

    private var diagnosticCollector = DiagnosticCollector<JavaFileObject>()

    private val tool by lazy { JavacTool.create() }

    private val standardFileManager by lazy {
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

    override fun getDiagnosticIssues(): List<JavaDiagnosticIssue> {
        val diagnostics = diagnosticCollector.diagnostics
        val issues = mutableListOf<JavaDiagnosticIssue>()
        diagnostics.forEach { diagnostic ->
            if (diagnostic.source == null) return@forEach
            val severity = when (diagnostic.kind) {
                Diagnostic.Kind.ERROR -> DiagnosticIssue.Severity.Error
                Diagnostic.Kind.WARNING,
                Diagnostic.Kind.MANDATORY_WARNING -> DiagnosticIssue.Severity.Warning
                else -> DiagnosticIssue.Severity.None
            }
            issues += JavaDiagnosticIssue(
                message = diagnostic.getMessage(Locale.getDefault()),
                severity = severity,
                startIndex = diagnostic.startPosition.toInt(),
                endIndex = diagnostic.endPosition.toInt(),
                source = diagnostic.source,
                code = diagnostic.code
            )
        }
        return issues
    }

    override fun diagnose() {
        standardFileManager.setLocation(StandardLocation.CLASS_PATH, getClasspath())
        standardFileManager.autoClose = false
        val newArgs = args.toMutableList()
        newArgs.apply {
            add("-source")
            add("1.8")
            add("-target")
            add("1.8")
        }
        tool.getTask(System.out.writer(), standardFileManager, diagnosticCollector, newArgs, null, getSourceFiles())
            .apply {
                parse()
                analyze()
                if (diagnosticCollector.diagnostics.isEmpty()) {
                    try {
                        generate().forEach(Cache::saveCache)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
    }

    override fun reset() {
        diagnosticCollector = DiagnosticCollector<JavaFileObject>()
    }

    private fun getClasspath(): List<File> {
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
                if (Cache.getCache(it) != null && Cache.getCache(it)!!.lastModified == it.lastModified()) {
                    classpath.add(it)
                }
            }
        return classpath
    }

    private fun getSourceFiles(): List<JavaFileObject> {
        val sourceFiles = mutableListOf<JavaFileObject>()
        project.srcDir.walk().forEach {
            if (it.extension == "java") {
                val cache = Cache.getCache(it)
                if (cache == null || cache.lastModified < it.lastModified()) {
                    sourceFiles.add(Cache.saveCache(it))
                }
            }
        }
        return sourceFiles
    }

    private object Cache {

        val cacheMap = mutableMapOf<String, JavaFileObject>()
        fun saveCache(file: File): JavaFileObject {
            val obj = object : SimpleJavaFileObject(file.toURI(), JavaFileObject.Kind.SOURCE) {
                private val lastModified = file.lastModified()
                override fun getCharContent(ignoreEncodingErrors: Boolean): CharSequence {
                    return file.readText()
                }

                override fun getLastModified(): Long {
                    return lastModified
                }
            }
            cacheMap[file.absolutePath] = obj
            return obj
        }

        fun saveCache(obj: JavaFileObject) {
            cacheMap[obj.name] = obj
        }

        fun getCache(key: File): JavaFileObject? {
            return cacheMap[key.absolutePath]
        }

    }
}