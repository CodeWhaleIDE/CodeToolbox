package com.bluewhaleyt.codetoolbox.editor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.IDEEditorState
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

@Composable
fun IDEEditor(
    modifier: Modifier = Modifier,
    state: IDEEditorState,
    onInitialize: (IDEEditor) -> Unit = {},
    onReset: (IDEEditor) -> Unit = {},
    onRelease: (IDEEditor) -> Unit = { it.release() },
    update: (IDEEditor) -> Unit = {}
) {
    val context = LocalContext.current
    val editor = remember {
        IDEEditor(context)
    }.also {
        state.editor = it
        onInitialize(it)
    }

    AndroidView(
        modifier = modifier,
        factory = { editor },
        onReset = onReset,
        onRelease = onRelease,
        update = update
    )

    LaunchedEffect(key1 = state.text) {
        editor.setText(state.text)
    }

    LaunchedEffect(key1 = state.typefaceText) {
        editor.typefaceText = state.typefaceText
    }

    LaunchedEffect(key1 = state.typefaceLineNumber) {
        editor.typefaceLineNumber = state.typefaceLineNumber
    }

    LaunchedEffect(key1 = state.textSize) {
        editor.setTextSize(state.textSize)
    }

    LaunchedEffect(key1 = state.colorScheme) {
        editor.colorScheme = state.colorScheme(editor)
    }

    LaunchedEffect(key1 = state.language) {
        editor.setEditorLanguage(state.language(editor))
    }

}