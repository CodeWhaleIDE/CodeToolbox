package com.bluewhaleyt.codetoolbox

import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import com.bluewhaleyt.codetoolbox.editor.IDEEditorDiagnostics
import com.bluewhaleyt.codetoolbox.editor.IDEEditorState
import com.bluewhaleyt.codetoolbox.editor.ui.IDEEditor
import com.bluewhaleyt.codetoolbox.editor.utils.loadTheme
import com.bluewhaleyt.codetoolbox.editor.utils.toDiagnosticRegion
import com.bluewhaleyt.codetoolbox.language.java.language.JavaIDETextMateLanguage
import com.bluewhaleyt.codetoolbox.language.java.project.JavaProject
import io.github.rosemoe.sora.event.SelectionChangeEvent
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import io.github.rosemoe.sora.text.ContentIO
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.subscribeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        val fileRegistry = FileProviderRegistry.getInstance()
        fileRegistry.addFileProvider(AssetsFileResolver(context.assets))

        val grammarRegistry = GrammarRegistry.getInstance()
        grammarRegistry.loadGrammars("textmate/language/languages.json")

        val themeRegistry = ThemeRegistry.getInstance()
        themeRegistry.loadTheme("textmate/theme/darcula.json")
        themeRegistry.setTheme("darcula")
    }

    val project = JavaProject.create(ROOT_DIR, "MyProject")
    val file = project.getSourceFile("Main.java")

    file?.let { file ->
        LaunchedEffect(key1 = Unit) {
            viewModel.state = viewModel.state.copy(
                text = ContentIO.createFrom(file.inputStream()),
            )
        }
        Column(Modifier.imePadding()) {
            IDEEditor(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = viewModel.state.copy(
                    project = project,
                    file = file,
                    language = { JavaIDETextMateLanguage(it, project, file) },
                    diagnosticsEnabled = true
                ),
                onInitialize = {
                    editor = it
                }
            )
        }
    }

}

class MainViewModel : ViewModel() {
    var state by mutableStateOf(IDEEditorState())
}