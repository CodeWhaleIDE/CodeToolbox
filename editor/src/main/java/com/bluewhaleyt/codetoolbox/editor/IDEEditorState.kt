package com.bluewhaleyt.codetoolbox.editor

import android.graphics.Typeface
import com.bluewhaleyt.codetoolbox.core.project.Project
import com.bluewhaleyt.codetoolbox.editor.language.IDETextMateLanguage
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentIO
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import java.io.File

data class IDEEditorState(
    val project: Project? = null,
    val file: File? = null,
    val text: CharSequence = defaultText(file),
    val textSize: Float = CodeEditor.DEFAULT_TEXT_SIZE.toFloat(),
    val typeface: Typeface = Typeface.DEFAULT,
    val colorScheme: (IDEEditor) -> EditorColorScheme = { TextMateColorScheme.create(ThemeRegistry.getInstance()) },
    val language: (IDEEditor) -> IDETextMateLanguage = { IDETextMateLanguage(it) },
    val diagnosticsEnabled: Boolean = false
)

private fun defaultText(file: File?) =
    if (file != null && file.exists() && file.isFile) {
        ContentIO.createFrom(file.inputStream())
    } else Content()