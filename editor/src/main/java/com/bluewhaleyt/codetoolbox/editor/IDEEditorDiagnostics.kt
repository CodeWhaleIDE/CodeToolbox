package com.bluewhaleyt.codetoolbox.editor

import com.bluewhaleyt.codetoolbox.core.diagnostic.DiagnosticIssue
import com.bluewhaleyt.codetoolbox.core.diagnostic.Diagnostor
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.event.EventReceiver
import io.github.rosemoe.sora.event.Unsubscribe
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticRegion
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticsContainer
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class IDEEditorDiagnostics(
    val editor: IDEEditor,
    val file: File,
    val diagnostor: Diagnostor,
    val diagnostics: (List<DiagnosticIssue>) -> List<DiagnosticRegion>
) : EventReceiver<ContentChangeEvent> {

    private val diagnosticsContainer = DiagnosticsContainer()

    init {
        diagnose(editor.text)
    }

    override fun onReceive(event: ContentChangeEvent, unsubscribe: Unsubscribe) {
        diagnose(event.editor.text)
    }

    private fun diagnose(text: Content) = CoroutineScope(Dispatchers.IO).launch {
        ContentIO.writeTo(text, file.outputStream(), true)

        diagnostor.reset()
        diagnostor.diagnose()

        diagnosticsContainer.reset()
        diagnosticsContainer.addDiagnostics(
            diagnostics(diagnostor.getDiagnosticIssues())
        )

        editor.diagnostics = diagnosticsContainer
    }

}