package com.bluewhaleyt.codetoolbox.core.utils

import android.content.res.AssetManager
import java.nio.charset.Charset

fun AssetManager.readFile(fileName: String, charset: Charset = Charset.defaultCharset()) =
    open(fileName).bufferedReader(charset).use { it.readText() }