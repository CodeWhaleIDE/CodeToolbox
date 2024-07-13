package com.bluewhaleyt.codetoolbox.core.completion

import android.graphics.drawable.Drawable

sealed interface CodeCompletion {
    val prefix: String
    val description: String?
    val icon: Drawable?

    data class Word(
        override val prefix: String,
        override val description: String? = null,
        override val icon: Drawable? = null,
        val commitText: String = prefix,
        val kind: Kind
    ) : CodeCompletion

    data class Snippet(
        override val prefix: String,
        override val description: String? = null,
        override val icon: Drawable? = null,
        val body: String
    ) : CodeCompletion

    enum class Kind {
        Text,
        Keyword,
        Variable
    }
}