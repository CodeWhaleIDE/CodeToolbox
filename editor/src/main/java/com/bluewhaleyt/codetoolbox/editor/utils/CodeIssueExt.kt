package com.bluewhaleyt.codetoolbox.editor.utils

import com.bluewhaleyt.codetoolbox.core.issue.CodeIssue
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticDetail
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticRegion
import io.github.rosemoe.sora.lang.diagnostic.Quickfix

fun CodeIssue.toDiagnosticRegion(
    briefMessage: String = message,
    detailedMessage: String? = null,
    quickfixes: List<Quickfix>? = null,
    extraData: Any? = null
) = run {
    val severity = when (severity) {
        CodeIssue.Severity.None -> DiagnosticRegion.SEVERITY_NONE
        CodeIssue.Severity.Typo -> DiagnosticRegion.SEVERITY_TYPO
        CodeIssue.Severity.Warning -> DiagnosticRegion.SEVERITY_WARNING
        CodeIssue.Severity.Error -> DiagnosticRegion.SEVERITY_ERROR
    }
    DiagnosticRegion(
        startIndex,
        endIndex,
        severity,
        0,
        DiagnosticDetail(
            briefMessage = briefMessage,
            detailedMessage = detailedMessage,
            quickfixes = quickfixes,
            extraData = extraData
        )
    )
}