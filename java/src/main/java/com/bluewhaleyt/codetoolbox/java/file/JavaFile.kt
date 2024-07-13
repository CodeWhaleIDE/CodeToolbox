package com.bluewhaleyt.codetoolbox.java.file

import com.bluewhaleyt.codetoolbox.java.project.JavaProject
import java.io.File

class JavaFile internal constructor(
    override val project: JavaProject,
    override val baseFile: File
) : JvmFile(project, baseFile)