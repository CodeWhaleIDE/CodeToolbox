package com.bluewhaleyt.codetoolbox.editor.utils

import com.bluewhaleyt.codetoolbox.core.diagnostics.DiagnosticIssue
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticDetail
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticRegion
import io.github.rosemoe.sora.lang.diagnostic.Quickfix

fun DiagnosticIssue.toDiagnosticRegion(
    briefMessage: String? = message,
    detailedMessage: String? = null,
    quickfixes: List<Quickfix>? = null,
    extraData: Any? = null
) = toDiagnosticRegionImpl().apply {
    detail = briefMessage?.let {
        DiagnosticDetail(
            briefMessage = briefMessage,
            detailedMessage = detailedMessage,
            quickfixes = quickfixes,
            extraData = extraData
        )
    }
}

private fun DiagnosticIssue.toDiagnosticRegionImpl() = run {
    val severity = when (severity) {
        DiagnosticIssue.Severity.None -> DiagnosticRegion.SEVERITY_NONE
        DiagnosticIssue.Severity.Typo -> DiagnosticRegion.SEVERITY_TYPO
        DiagnosticIssue.Severity.Warning -> DiagnosticRegion.SEVERITY_WARNING
        DiagnosticIssue.Severity.Error -> DiagnosticRegion.SEVERITY_ERROR
    }
    DiagnosticRegion(
        startIndex,
        endIndex,
        severity,
        0,
        null
    )
}