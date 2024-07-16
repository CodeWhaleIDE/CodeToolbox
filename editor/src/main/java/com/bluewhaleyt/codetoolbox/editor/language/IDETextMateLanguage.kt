package com.bluewhaleyt.codetoolbox.editor.language

import android.os.Bundle
import com.bluewhaleyt.codetoolbox.core.project.Project
import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.IDEEditorDiagnostics
import com.bluewhaleyt.codetoolbox.editor.utils.getPrefix
import com.bluewhaleyt.codetoolbox.editor.utils.toDiagnosticRegion
import io.github.rosemoe.sora.lang.completion.CompletionItemKind
import io.github.rosemoe.sora.lang.completion.CompletionPublisher
import io.github.rosemoe.sora.lang.completion.SimpleCompletionItem
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.widget.subscribeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

open class IDETextMateLanguage(
    open val editor: IDEEditor,
    open val project: Project? = null,
    open val file: File? = null,
    private val scopeName: String = "",
    var builtInIdentifierCompletionEnabled: Boolean = true
) : TextMateLanguage(
    grammar(scopeName),
    languageConfiguration(scopeName),
    grammarRegistry,
    themeRegistry,
    builtInIdentifierCompletionEnabled
) {
    var keywordCompletionEnabled = true

    open val keywords = emptyList<String>()

    override fun requireAutoComplete(
        content: ContentReference,
        position: CharPosition,
        publisher: CompletionPublisher,
        extraArguments: Bundle
    ) {
        if (builtInIdentifierCompletionEnabled)
            super.requireAutoComplete(content, position, publisher, extraArguments)

        val prefix = content.getPrefix(position)

        if (keywordCompletionEnabled) {
            keywords.forEach { keyword ->
                if (keyword.startsWith(prefix) && prefix.isNotBlank()) {
                    publisher.addItem(
                        SimpleCompletionItem(
                            keyword,
                            "Keyword",
                            prefix.length,
                            keyword
                        ).also {
                            it.kind(CompletionItemKind.Keyword)
                        }
                    )
                }
            }
        }
    }

    companion object {
        private val grammarRegistry = GrammarRegistry.getInstance()
        private val themeRegistry = ThemeRegistry.getInstance()
        private fun grammar(scopeName: String) = grammarRegistry.findGrammar(scopeName)
        private fun languageConfiguration(scopeName: String) = grammarRegistry.findLanguageConfiguration(scopeName)
    }

}