package com.bluewhaleyt.codetoolbox

import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bluewhaleyt.codetoolbox.core.completion.CodeCompletion
import com.bluewhaleyt.codetoolbox.editor.IDEEditorState
import com.bluewhaleyt.codetoolbox.editor.ui.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.utils.readTextAsContentIO
import com.bluewhaleyt.codetoolbox.editor.utils.textMateColorScheme
import com.bluewhaleyt.codetoolbox.java.JavaIDEEditorLanguage
import com.bluewhaleyt.codetoolbox.java.project.JavaProject
import java.io.File

val ROOT_DIR = File(Environment.getExternalStorageDirectory(), "CodeToolbox")
    .also { if (!it.exists()) it.mkdirs() }

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val project = JavaProject.create(context, ROOT_DIR, "MyProject")
    val file = project.getSourceFile("Main.java")

    file?.let {
        val editorState = remember {
            IDEEditorState(
                colorScheme = { it.textMateColorScheme },
                language = { JavaIDEEditorLanguage(it, project, file, true).apply {
                    completions += listOf(
                        CodeCompletion.Word(
                            prefix = "for",
                            kind = CodeCompletion.Kind.Keyword
                        ),
                        CodeCompletion.Snippet(
                            prefix = "fori",
                            description = "For loop index",
                            body = """
                                for (int i = 0; i <= $1; i++) {
                                    $2
                                }
                            """.trimIndent()
                        )
                    )
                } }
            )
        }
        Column {
            IDEEditor(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = editorState.copy(
                    text = file.readTextAsContentIO()
                )
            )
        }
    }


}