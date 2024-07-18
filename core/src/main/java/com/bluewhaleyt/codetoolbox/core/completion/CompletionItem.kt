package com.bluewhaleyt.codetoolbox.core.completion

import android.graphics.drawable.Drawable

sealed interface CompletionItem {
    val prefix: String
    val label: String
    val description: String?
    val icon: Drawable?
    val kind: Kind

    data class Word(
        override val prefix: String,
        override val label: String = prefix,
        override val description: String?,
        override val icon: Drawable? = null,
        override val kind: Kind,
        val commitText: String,
    ) : CompletionItem

    data class Keyword(
        override val prefix: String,
        override val label: String = prefix,
        override val description: String? = "Keyword",
        override val icon: Drawable? = null,
        val commitText: String = prefix
    ) : CompletionItem {
        override val kind = Kind.Keyword
    }

    data class Snippet(
        override val prefix: String,
        override val label: String = prefix,
        override val description: String?,
        override val icon: Drawable? = null,
        val body: String,
        val deleteSelected: Boolean = true
    ) : CompletionItem {
        override val kind = Kind.Snippet
    }

    enum class Kind {
        Identifier,
        Text,
        Method,
        Function,
        Constructor,
        Field,
        Variable,
        Class,
        Interface,
        Module,
        Property,
        Unit,
        Value,
        Enum,
        Keyword,
        Snippet,
        Color,
        Reference,
        File,
        Folder,
        EnumMember,
        Constant,
        Struct,
        Event,
        Operator,
        TypeParameter,
        User,
        Issue;
    }
}