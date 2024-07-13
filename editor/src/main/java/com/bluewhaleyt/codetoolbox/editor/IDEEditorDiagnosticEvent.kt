package com.bluewhaleyt.codetoolbox.editor

import com.bluewhaleyt.codetoolbox.core.analyzer.Analyzer
import com.bluewhaleyt.codetoolbox.core.issue.CodeIssue
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.event.EventReceiver
import io.github.rosemoe.sora.event.Unsubscribe
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticRegion
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticsContainer
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentIO
import io.github.rosemoe.sora.widget.CodeEditor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class IDEEditorDiagnosticEvent(
    val editor: CodeEditor,
    val file: File,
    val analyzer: Analyzer,
    val onAddDiagnostics: (List<CodeIssue>) -> List<DiagnosticRegion>
) : EventReceiver<ContentChangeEvent> {

    private val diagnostics = DiagnosticsContainer()

    init {
        analyze(editor.text)
    }

    override fun onReceive(event: ContentChangeEvent, unsubscribe: Unsubscribe) {
        analyze(event.editor.text)
    }

    private fun analyze(content: Content) = CoroutineScope(Dispatchers.IO).launch {
        ContentIO.writeTo(content, file.outputStream(), true)
        analyzer.reset()

        analyzer.analyze()
        diagnostics.reset()
        diagnostics.addDiagnostics(onAddDiagnostics(analyzer.getIssues()))

        editor.diagnostics = diagnostics
    }

}