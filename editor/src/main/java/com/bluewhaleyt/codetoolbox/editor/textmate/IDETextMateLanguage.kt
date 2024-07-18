package com.bluewhaleyt.codetoolbox.editor.textmate

import android.os.Bundle
import com.bluewhaleyt.codetoolbox.core.completion.Completion
import com.bluewhaleyt.codetoolbox.core.completion.CompletionItem
import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.utils.getPrefix
import io.github.rosemoe.sora.lang.analysis.StyleReceiver
import io.github.rosemoe.sora.lang.completion.CompletionItemKind
import io.github.rosemoe.sora.lang.completion.CompletionPublisher
import io.github.rosemoe.sora.lang.completion.SimpleCompletionItem
import io.github.rosemoe.sora.lang.completion.SimpleSnippetCompletionItem
import io.github.rosemoe.sora.lang.completion.SnippetDescription
import io.github.rosemoe.sora.lang.completion.snippet.parser.CodeSnippetParser
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion
import io.github.rosemoe.sora.widget.getComponent

open class IDETextMateLanguage(
    open val editor: IDEEditor,
    private val scopeName: String,
    var createIdentifiers: Boolean = true
) : TextMateLanguage(
    getGrammar(scopeName),
    getLanguageConfiguration(scopeName),
    grammarRegistry,
    themeRegistry,
    createIdentifiers
) {
    open val completion: Completion? = null

    // instantiate without language
    constructor(editor: IDEEditor) : this(editor = editor, scopeName = "")

    override fun requireAutoComplete(
        content: ContentReference,
        position: CharPosition,
        publisher: CompletionPublisher,
        extraArguments: Bundle
    ) {
        val prefix = content.getPrefix(position)

        if (createIdentifiers)
            super.requireAutoComplete(content, position, publisher, extraArguments)

        publisher.setComparator(Comparator<io.github.rosemoe.sora.lang.completion.CompletionItem> { o1, o2 ->
            if (o1.label[0].isLowerCase() && o2.label[0].isUpperCase()) {
                return@Comparator -1
            } else if (o1.label[0].isUpperCase() && o2.label[0].isLowerCase()) {
                return@Comparator 1
            }
            return@Comparator o1.label.toString().compareTo(o2.label.toString())
        })

        completion?.items?.forEach { item ->
            if (item.prefix.startsWith(prefix) && prefix.isNotBlank()) {
                val finalItem = when (item) {
                    is CompletionItem.Word -> {
                        SimpleCompletionItem(
                            item.label,
                            "${item.kind.name} - ${item.description}",
                            item.icon,
                            prefix.length,
                            item.commitText
                        ).also { it.kind(CompletionItemKind.valueOf(item.kind.name)) }
                    }
                    is CompletionItem.Keyword -> {
                        SimpleCompletionItem(
                            item.label,
                            "${item.kind.name} - ${item.description}",
                            item.icon,
                            prefix.length,
                            item.commitText
                        ).also { it.kind(CompletionItemKind.valueOf(item.kind.name)) }
                    }
                    is CompletionItem.Snippet -> {
                        SimpleSnippetCompletionItem(
                            item.label,
                            "${item.kind.name} - ${item.description}",
                            item.icon,
                            SnippetDescription(
                                selectedLength = prefix.length,
                                snippet = CodeSnippetParser.parse(item.body),
                                deleteSelected = item.deleteSelected
                            )
                        ).also { it.kind(CompletionItemKind.valueOf(item.kind.name)) }
                    }
                }
                publisher.addItem(finalItem)
            }
        }
    }

    companion object {
        private val grammarRegistry = GrammarRegistry.getInstance()
        private val themeRegistry = ThemeRegistry.getInstance()
        private fun getGrammar(scopeName: String) = grammarRegistry.findGrammar(scopeName)
        private fun getLanguageConfiguration(scopeName: String) = grammarRegistry.findLanguageConfiguration(scopeName)
    }
}