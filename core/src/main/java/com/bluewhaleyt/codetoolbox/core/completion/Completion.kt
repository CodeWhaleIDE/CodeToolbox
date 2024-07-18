package com.bluewhaleyt.codetoolbox.core.completion

import android.content.Context
import com.bluewhaleyt.codetoolbox.core.utils.readFile

interface Completion {
    val context: Context
    val snippetAssetsDirPath: String
    val currentComposingText: String

    val items: List<CompletionItem>

    fun getSnippetFileContent(fileName: String) =
        context.assets.readFile("$snippetAssetsDirPath/$fileName")
}