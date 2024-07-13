package com.bluewhaleyt.codetoolbox.java

import com.bluewhaleyt.codetoolbox.core.analyzer.Analyzer
import com.bluewhaleyt.codetoolbox.core.issue.CodeIssue
import com.bluewhaleyt.codetoolbox.java.issue.JavaCodeIssue
import com.bluewhaleyt.codetoolbox.java.project.JavaProject
import java.io.File
import java.util.Locale
import javax.tools.Diagnostic
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.SimpleJavaFileObject
import javax.tools.StandardLocation

class JavaAnalyzer(
    private val project: JavaProject
) : Analyzer {

    override fun getIssues(): List<JavaCodeIssue> {
        val diagnostics = project.environment.diagnosticCollector.diagnostics
        val issues = mutableListOf<JavaCodeIssue>()
        diagnostics.forEach { diagnostic ->
            if (diagnostic.source == null) return@forEach
            val severity = when (diagnostic.kind) {
                Diagnostic.Kind.ERROR -> CodeIssue.Severity.Error
                Diagnostic.Kind.WARNING,
                Diagnostic.Kind.MANDATORY_WARNING -> CodeIssue.Severity.Warning
                else -> CodeIssue.Severity.None
            }
            issues += JavaCodeIssue(
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

    override fun analyze() {
        with (project.environment) {
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
    }

    override fun reset() {
        project.environment.diagnosticCollector = DiagnosticCollector()
    }

    internal object Cache {

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