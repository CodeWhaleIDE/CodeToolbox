package com.bluewhaleyt.codetoolbox.editor.language

import android.os.Bundle
import com.bluewhaleyt.codetoolbox.core.completion.CodeCompletion
import com.bluewhaleyt.codetoolbox.core.issue.CodeIssue
import com.bluewhaleyt.codetoolbox.core.project.Project
import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.IDEEditorDiagnosticEvent
import com.bluewhaleyt.codetoolbox.editor.utils.toDiagnosticRegion
import io.github.rosemoe.sora.lang.completion.CompletionHelper
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
import io.github.rosemoe.sora.util.MyCharacter
import io.github.rosemoe.sora.widget.subscribeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

abstract class IDEEditorLanguage(
    open val editor: IDEEditor,
    open val project: Project,
    open val file: File,
    open val scopeName: String,
    open val createIdentifiers: Boolean
) : TextMateLanguage(
    GrammarRegistry.getInstance().findGrammar(scopeName),
    GrammarRegistry.getInstance().findLanguageConfiguration(scopeName),
    GrammarRegistry.getInstance(),
    ThemeRegistry.getInstance(),
    createIdentifiers
) {

    abstract var keywords: List<String>

    var completions = emptyList<CodeCompletion>()

    var issues = emptyList<CodeIssue>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            editor.post {
                editor.subscribeEvent(
                    IDEEditorDiagnosticEvent(
                        editor = editor,
                        file = file,
                        analyzer = project.analyzer,
                        onAddDiagnostics = { issuesFromAnalyzer ->
                            issues.map { it.toDiagnosticRegion() } +
                                    issuesFromAnalyzer.map { it.toDiagnosticRegion() }
                        }
                    )
                )
            }
        }
    }

    override fun requireAutoComplete(
        content: ContentReference,
        position: CharPosition,
        publisher: CompletionPublisher,
        extraArguments: Bundle
    ) {
        if (createIdentifiers) {
            super.requireAutoComplete(content, position, publisher, extraArguments)
        }

        val prefix = CompletionHelper.computePrefix(content, position, MyCharacter::isJavaIdentifierPart)

        completions.forEach { completion ->
            if (completion.prefix.startsWith(prefix) && prefix.isNotBlank()) {
                when (completion) {
                    is CodeCompletion.Word -> {
                        publisher.addItem(
                            SimpleCompletionItem(
                                completion.prefix,
                                completion.description,
                                completion.icon,
                                prefix.length,
                                completion.commitText
                            ).also {
                                val kind = when (completion.kind) {
                                    CodeCompletion.Kind.Text -> CompletionItemKind.Text
                                    CodeCompletion.Kind.Keyword -> CompletionItemKind.Keyword
                                    CodeCompletion.Kind.Variable -> CompletionItemKind.Variable
                                }
                                it.kind(kind)
                            }
                        )
                    }
                    is CodeCompletion.Snippet -> {
                        publisher.addItem(
                            SimpleSnippetCompletionItem(
                                completion.prefix,
                                completion.description,
                                completion.icon,
                                SnippetDescription(
                                    selectedLength = prefix.length,
                                    snippet = CodeSnippetParser.parse(completion.body)
                                )
                            )
                        )
                    }
                }
            }
        }

        keywords.forEach {
            if (it.startsWith(prefix) && prefix.isNotBlank()) {
                publisher.addItem(
                    SimpleCompletionItem(
                        it,
                        prefix.length,
                        it
                    ).also {
                        it.kind(CompletionItemKind.Keyword)
                    }
                )
            }
        }
    }

}