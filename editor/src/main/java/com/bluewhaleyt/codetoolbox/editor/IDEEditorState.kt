package com.bluewhaleyt.codetoolbox.editor

import android.graphics.Typeface
import io.github.rosemoe.sora.lang.EmptyLanguage
import io.github.rosemoe.sora.lang.Language
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

data class IDEEditorState(
    val text: Content = Content(),
    val typefaceText: Typeface = Typeface.MONOSPACE,
    val typefaceLineNumber: Typeface = Typeface.MONOSPACE,
    val textSize: Float = CodeEditor.DEFAULT_TEXT_SIZE.toFloat(),
    val colorScheme: (IDEEditor) -> EditorColorScheme = { EditorColorScheme.getDefault() },
    val language: (IDEEditor) -> Language = { EmptyLanguage() }
) {
    lateinit var editor: IDEEditor
}