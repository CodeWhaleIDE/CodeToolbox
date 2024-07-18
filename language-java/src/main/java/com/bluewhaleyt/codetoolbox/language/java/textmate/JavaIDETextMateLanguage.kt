package com.bluewhaleyt.codetoolbox.language.java.textmate

import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.textmate.IDETextMateLanguage
import com.bluewhaleyt.codetoolbox.editor.utils.currentComposingText
import com.bluewhaleyt.codetoolbox.language.java.completion.JavaCompletion

class JavaIDETextMateLanguage(
    override val editor: IDEEditor
) : IDETextMateLanguage(editor, "source.java") {

    override val completion by lazy {
        JavaCompletion(editor.context, editor.currentComposingText)
    }
}