package com.bluewhaleyt.codetoolbox.java.issue

import com.bluewhaleyt.codetoolbox.core.issue.CodeIssue
import javax.tools.JavaFileObject

data class JavaCodeIssue(
    override val message: String,
    override val severity: CodeIssue.Severity,
    override val startIndex: Int,
    override val endIndex: Int,
    val source: JavaFileObject,
    val code: String
) : CodeIssue