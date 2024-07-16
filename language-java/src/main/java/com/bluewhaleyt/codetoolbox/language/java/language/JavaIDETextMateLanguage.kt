package com.bluewhaleyt.codetoolbox.language.java.language

import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.language.IDETextMateLanguage
import com.bluewhaleyt.codetoolbox.language.java.project.JavaProject
import com.intellij.lang.java.JavaLanguage
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import java.io.File

class JavaIDETextMateLanguage(
    override val editor: IDEEditor,
    override val project: JavaProject?,
    override val file: File?,
) : IDETextMateLanguage(editor, project, file, "source.java") {

    override val keywords = listOf(
        "abstract", "assert", "boolean", "break", "byte", "case", "catch",
        "char", "class", "const", "continue", "default", "do", "double",
        "else", "enum", "extends", "false", "final", "finally", "float",
        "for", "if", "implements", "import", "instanceof", "int", "interface",
        "long", "native", "new", "null", "package", "private", "protected",
        "public", "return", "short", "static", "strictfp", "super",
        "switch", "synchronized", "this", "throw", "throws", "transient",
        "true", "try", "void", "volatile", "while"
    )

}