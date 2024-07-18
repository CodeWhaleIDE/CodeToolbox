package com.bluewhaleyt.codetoolbox.editor

import android.graphics.Typeface
import com.bluewhaleyt.codetoolbox.core.project.Project
import com.bluewhaleyt.codetoolbox.editor.textmate.IDETextMateLanguage
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

data class IDEEditorState(
    val text: CharSequence = Content(),
    val textSize: Float = CodeEditor.DEFAULT_TEXT_SIZE.toFloat(),
    val typeface: (IDEEditor) -> Typeface = { Typeface.createFromAsset(it.context.assets, "font/jetbrains-mono.ttf") },
    val colorScheme: (IDEEditor) -> EditorColorScheme = { TextMateColorScheme.create(ThemeRegistry.getInstance()) },
    val language: (IDEEditor) -> IDETextMateLanguage = { IDETextMateLanguage(it) },
    val project: Project? = null
)