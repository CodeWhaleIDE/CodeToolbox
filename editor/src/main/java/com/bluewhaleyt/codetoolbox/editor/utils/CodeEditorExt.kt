package com.bluewhaleyt.codetoolbox.editor.utils

import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentIO
import java.io.File

fun File.readTextAsContentIO() =
    ContentIO.createFrom(inputStream())

fun File.writeTextAsContentIO(text: Content) =
    ContentIO.writeTo(text, outputStream(), true)