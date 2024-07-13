package com.bluewhaleyt.codetoolbox.editor

import android.content.Context
import android.util.AttributeSet
import com.bluewhaleyt.codetoolbox.editor.utils.loadTheme
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import io.github.rosemoe.sora.widget.CodeEditor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.tm4e.core.registry.IThemeSource
import java.io.FileNotFoundException

class IDEEditor @JvmOverloads constructor(
    @get:JvmName("mContext") val context: Context,
    private val attrs: AttributeSet? = null
) : CodeEditor(context, attrs) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val fileProvider = AssetsFileResolver(context.assets)
            FileProviderRegistry.getInstance().addFileProvider(fileProvider)

            GrammarRegistry.getInstance().loadGrammars("textmate/language/languages.json")

            val themeRegistry = ThemeRegistry.getInstance()
            themeRegistry.loadTheme("textmate/theme/darcula.json")
            themeRegistry.setTheme("darcula")
        }
    }

}