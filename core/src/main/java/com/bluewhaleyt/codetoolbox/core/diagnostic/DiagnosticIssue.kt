package com.bluewhaleyt.codetoolbox.core.diagnostic

interface DiagnosticIssue {
    val message: String
    val severity: Severity
    val startIndex: Int
    val endIndex: Int

    enum class Severity {
        None,
        Typo,
        Warning,
        Error
    }
}