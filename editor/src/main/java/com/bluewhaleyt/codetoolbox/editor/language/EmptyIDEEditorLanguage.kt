package com.bluewhaleyt.codetoolbox.editor.language

import com.bluewhaleyt.codetoolbox.core.project.Project
import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import java.io.File

class EmptyIDEEditorLanguage(
    override val editor: IDEEditor,
    override val project: Project,
    override val file: File,
    override val scopeName: String,
    override val createIdentifiers: Boolean
) : IDEEditorLanguage(editor, project, file, scopeName, createIdentifiers) {

    override var keywords = emptyList<String>()

}