package com.bluewhaleyt.codetoolbox.editor.utils

import io.github.rosemoe.sora.lang.completion.CompletionHelper
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.util.MyCharacter
import io.github.rosemoe.sora.widget.CodeEditor
import org.eclipse.tm4e.core.registry.IThemeSource
import java.io.File
import java.io.FileNotFoundException

val CodeEditor.currentComposingText
    get() = ContentReference(text).getPrefix(CharPosition(cursor.leftLine, cursor.leftColumn))

internal fun ContentReference.getPrefix(position: CharPosition) =
    CompletionHelper.computePrefix(this, position, MyCharacter::isJavaIdentifierPart)

fun ThemeRegistry.loadTheme(
    filePath: String,
    themeName: String = File(filePath).nameWithoutExtension
) {
    val inputStream = FileProviderRegistry.getInstance()
        .tryGetInputStream(filePath) ?: throw FileNotFoundException("Theme file not found: $filePath")
    val source = IThemeSource.fromInputStream(inputStream, filePath, null)
    loadTheme(ThemeModel(source, themeName))
}