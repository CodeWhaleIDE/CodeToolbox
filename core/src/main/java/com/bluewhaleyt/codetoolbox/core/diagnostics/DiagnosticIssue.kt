package com.bluewhaleyt.codetoolbox.core.diagnostics

interface DiagnosticIssue {
    enum class Severity {
        None, Typo, Warning, Error
    }

    val startIndex: Int
    val endIndex: Int
    val severity: Severity
    val message: String?
}