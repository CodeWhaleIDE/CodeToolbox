package com.bluewhaleyt.codetoolbox.editor.textmate

import android.content.Context
import com.bluewhaleyt.codetoolbox.editor.utils.loadTheme
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import java.io.File

class TextMateHelper(private val context: Context) {

    private val grammarRegistry = GrammarRegistry.getInstance()
    private val themeRegistry = ThemeRegistry.getInstance()

    init {
        val fileProviderRegistry = FileProviderRegistry.getInstance()
        fileProviderRegistry.addFileProvider(AssetsFileResolver(context.assets))
    }

    fun loadGrammars(jsonPath: String) {
        grammarRegistry.loadGrammars(jsonPath)
    }

    fun loadTheme(
        filePath: String,
        themeName: String = File(filePath).nameWithoutExtension
    ) {
        themeRegistry.loadTheme(filePath, themeName)
    }

    fun setTheme(themeName: String) {
        themeRegistry.setTheme(themeName)
    }

}