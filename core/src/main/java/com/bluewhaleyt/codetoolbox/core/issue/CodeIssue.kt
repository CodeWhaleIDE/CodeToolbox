package com.bluewhaleyt.codetoolbox.core.issue

interface CodeIssue {
    val message: String
    val severity: Severity
    val startIndex: Int
    val endIndex: Int

    data class Simple(
        override val message: String,
        override val severity: Severity,
        override val startIndex: Int,
        override val endIndex: Int
    ) : CodeIssue

    enum class Severity {
        None,
        Typo,
        Warning,
        Error
    }
}