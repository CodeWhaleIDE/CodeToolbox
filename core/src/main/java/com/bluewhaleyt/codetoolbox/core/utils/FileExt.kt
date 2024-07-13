package com.bluewhaleyt.codetoolbox.core.utils

import com.bluewhaleyt.codetoolbox.core.CodeToolboxInternalApi
import java.io.File
import java.nio.charset.Charset

@CodeToolboxInternalApi
fun File.writeTextIfNotExist(text: String, charset: Charset = Charset.defaultCharset()) {
    if (!exists()) writeText(text, charset)
}

@CodeToolboxInternalApi
fun File.mkDirsIfNotExist() {
    if (!exists()) mkdirs()
}

@CodeToolboxInternalApi
fun List<File>.mkDirsIfNotExist() {
    forEach { it.mkDirsIfNotExist() }
}