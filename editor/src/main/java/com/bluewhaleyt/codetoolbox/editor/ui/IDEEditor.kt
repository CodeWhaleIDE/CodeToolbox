package com.bluewhaleyt.codetoolbox.editor.ui

import android.content.Context
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bluewhaleyt.codetoolbox.core.project.Project
import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.IDEEditorDiagnostics
import com.bluewhaleyt.codetoolbox.editor.IDEEditorState
import com.bluewhaleyt.codetoolbox.editor.utils.toDiagnosticRegion
import io.github.rosemoe.sora.widget.subscribeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun IDEEditor(
    modifier: Modifier = Modifier,
    state: IDEEditorState,
    onInitialize: (IDEEditor) -> Unit
) {
    val context = LocalContext.current
    val editor by rememberSaveable(stateSaver = getIDEEditorSaver(context, state.project, state.file)) {
        mutableStateOf(IDEEditor(context, state.project, state.file))
    }.also {
        onInitialize(it.value)
    }
    AndroidView(
        modifier = modifier,
        factory = { editor },
        onRelease = { it.release() }
    )
    LaunchedEffect(key1 = state.text) {
        editor.setText(state.text)
    }
    LaunchedEffect(key1 = state.textSize) {
        editor.setTextSize(state.textSize)
    }
    LaunchedEffect(key1 = state.typeface) {
        if (state.typeface != Typeface.DEFAULT) {
            editor.typefaceLineNumber = state.typeface
            editor.typefaceText = state.typeface
        }
    }
    LaunchedEffect(key1 = state.colorScheme) {
        editor.colorScheme = state.colorScheme(editor)
    }
    LaunchedEffect(key1 = state.language) {
        editor.setEditorLanguage(state.language(editor))
    }
    LaunchedEffect(key1 = state.diagnosticsEnabled) {
        editor.diagnosticsEnabled = state.diagnosticsEnabled
    }
}

private fun getIDEEditorSaver(
    context: Context,
    project: Project?,
    file: File?
) = run {
    val startX = "startX"
    val startY = "startY"
    val textSize = "textSize"
    mapSaver(
        save = { mapOf(
            startX to it.scroller.startX,
            startY to it.scroller.startY,
            textSize to it.textSizePx
        ) },
        restore = {
            IDEEditor(context, project, file).apply {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(100L)
                    scroller.startScroll(it[startX] as Int, it[startY] as Int, 0, 0)
                    textSizePx = it[textSize] as Float
                }
            }
        }
    )
}