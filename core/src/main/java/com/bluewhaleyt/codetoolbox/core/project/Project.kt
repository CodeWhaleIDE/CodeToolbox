package com.bluewhaleyt.codetoolbox.core.project

import com.bluewhaleyt.codetoolbox.core.diagnostic.Diagnostor
import java.io.File

interface Project {
    val dir: File

    val diagnostor: Diagnostor
}