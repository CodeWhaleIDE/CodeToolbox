package com.bluewhaleyt.codetoolbox.language.java.diagnostic

import com.bluewhaleyt.codetoolbox.core.diagnostic.DiagnosticIssue
import javax.tools.JavaFileObject

data class JavaDiagnosticIssue(
    override val message: String,
    override val severity: DiagnosticIssue.Severity,
    override val startIndex: Int,
    override val endIndex: Int,
    val source: JavaFileObject,
    val code: String
) : DiagnosticIssue