package com.bluewhaleyt.codetoolbox.java.file

import com.bluewhaleyt.codetoolbox.java.project.JvmProject
import java.io.File

abstract class JvmFile internal constructor(
    open val project: JvmProject,
    open val baseFile: File
) : File(baseFile.absolutePath)