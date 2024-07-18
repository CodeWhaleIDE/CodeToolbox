package com.bluewhaleyt.codetoolbox

import android.graphics.Typeface
import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bluewhaleyt.codetoolbox.editor.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.IDEEditorState
import com.bluewhaleyt.codetoolbox.editor.textmate.TextMateHelper
import com.bluewhaleyt.codetoolbox.editor.ui.IDEEditor
import com.bluewhaleyt.codetoolbox.language.java.textmate.JavaIDETextMateLanguage
import java.io.File

val ROOT_DIR = File(Environment.getExternalStorageDirectory(), "CodeToolbox")
    .also { if (!it.exists()) it.mkdirs() }

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = viewModel<MainViewModel>()
    var editor by remember { mutableStateOf<IDEEditor?>(null) }

    LaunchedEffect(key1 = Unit) {
        TextMateHelper(context).also {
            it.loadGrammars("textmate/language/languages.json")
            it.loadTheme("textmate/theme/darcula.json")
            it.setTheme("darcula")
        }
    }
    Column {
        IDEEditor(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = viewModel.state.copy(
                text = "public",
                language = { JavaIDETextMateLanguage(it) },
            ),
            onInitialize = {
                editor = it
            }
        )
    }
}

class MainViewModel : ViewModel() {
    var state by mutableStateOf(IDEEditorState())
}