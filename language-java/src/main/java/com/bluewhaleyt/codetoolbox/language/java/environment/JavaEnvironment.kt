package com.bluewhaleyt.codetoolbox.language.java.environment

import com.bluewhaleyt.codetoolbox.language.java.project.JavaProject
import com.intellij.core.JavaCoreApplicationEnvironment
import com.intellij.core.JavaCoreProjectEnvironment
import com.sun.tools.javac.api.JavacTool
import com.sun.tools.javac.file.JavacFileManager
import java.nio.charset.Charset
import java.util.Locale
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.StandardLocation

class JavaEnvironment internal constructor(
    val project: JavaProject,
    val coreEnvironment: JavaCoreProjectEnvironment
) {

    companion object {
        fun create(project: JavaProject) = JavaEnvironment(
            project = project,
            coreEnvironment = JavaCoreProjectEnvironment({}, JavaCoreApplicationEnvironment {})
        )
    }

}