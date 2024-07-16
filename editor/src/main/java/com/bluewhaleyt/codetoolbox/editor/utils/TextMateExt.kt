package com.bluewhaleyt.codetoolbox.editor.utils

import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import org.eclipse.tm4e.core.registry.IThemeSource
import java.io.File
import java.io.FileNotFoundException

fun ThemeRegistry.loadTheme(filePath: String) {
    val inputStream = FileProviderRegistry.getInstance()
        .tryGetInputStream(filePath) ?: throw FileNotFoundException("Theme file not found: $filePath")
    val source = IThemeSource.fromInputStream(inputStream, filePath, null)
    loadTheme(ThemeModel(source, File(filePath).nameWithoutExtension))
}