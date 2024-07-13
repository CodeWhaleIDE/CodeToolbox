package com.bluewhaleyt.codetoolbox.java

import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.language.IDEEditorLanguage
import com.bluewhaleyt.codetoolbox.java.file.JavaFile
import com.bluewhaleyt.codetoolbox.java.project.JavaProject

class JavaIDEEditorLanguage(
    override val editor: IDEEditor,
    override val project: JavaProject,
    override val file: JavaFile,
    override val createIdentifiers: Boolean
) : IDEEditorLanguage(editor, project, file, "source.java", createIdentifiers) {

    override var keywords = listOf(
        "abstract", "assert", "boolean", "break", "byte", "case", "catch",
        "char", "class", "const", "continue", "default", "do", "double",
        "else", "enum", "extends", "final", "finally", "float", "for",
        "if", "implements", "import", "instanceof", "int", "interface",
        "long", "native", "new", "package", "private", "protected",
        "public", "return", "short", "static", "strictfp", "super",
        "switch", "synchronized", "this", "throw", "throws", "transient",
        "try", "void", "volatile", "while"
    )

}