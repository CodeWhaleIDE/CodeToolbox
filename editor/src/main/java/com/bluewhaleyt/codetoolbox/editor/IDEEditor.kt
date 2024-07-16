package com.bluewhaleyt.codetoolbox.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.bluewhaleyt.codetoolbox.core.project.Project
import com.bluewhaleyt.codetoolbox.editor.utils.toDiagnosticRegion
import io.github.rosemoe.sora.event.ContentChangeEvent
import io.github.rosemoe.sora.event.SubscriptionReceipt
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow
import io.github.rosemoe.sora.widget.getComponent
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import io.github.rosemoe.sora.widget.subscribeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@SuppressLint("ViewConstructor")
class IDEEditor @JvmOverloads constructor(
    @get:JvmName("mContext") val context: Context,
    val project: Project?,
    val file: File?,
    private val attrs: AttributeSet? = null
) : CodeEditor(context, attrs) {

    var diagnosticsEnabled = false
        set(value) {
            field = value
            if (value) setupDiagnostics()
        }

    init {
        Typeface.createFromAsset(context.assets, "font/jetbrains-mono.ttf").also {
            typefaceText = it
            typefaceLineNumber = it
        }
        setLineSpacing(2f, 1.5f)
        props.indicatorWaveWidth = 2f
        props.indicatorWaveAmplitude = 0.1f
    }

    private fun setupDiagnostics() {
        CoroutineScope(Dispatchers.IO).launch {
            project?.let { project ->
                file?.let { file ->
                    post {
                        subscribeEvent(
                            IDEEditorDiagnostics(
                                editor = this@IDEEditor,
                                file = file,
                                diagnostor = project.diagnostor,
                                diagnostics = { diagnosticIssues ->
                                    diagnosticIssues.map { it.toDiagnosticRegion() }
                                }
                            )
                        )
                    }
                }
            }
        }
    }

}