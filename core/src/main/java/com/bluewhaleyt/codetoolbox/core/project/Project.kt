package com.bluewhaleyt.codetoolbox.core.project

import android.content.Context
import com.bluewhaleyt.codetoolbox.core.analyzer.Analyzer
import java.io.File

interface Project {
    val context: Context
    val dir: File

    val name
        get() = dir.name

    val analyzer: Analyzer

    fun create()

    fun delete() {
        if (dir.isDirectory && dir.name == name) {
            dir.deleteRecursively()
        } else {
            throw IllegalStateException("Failed to delete directory: ${dir.absolutePath}.")
        }
    }
}