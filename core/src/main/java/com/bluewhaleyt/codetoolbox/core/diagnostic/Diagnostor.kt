package com.bluewhaleyt.codetoolbox.core.diagnostic

import com.bluewhaleyt.codetoolbox.core.project.Project

interface Diagnostor {
    val project: Project
    fun getDiagnosticIssues(): List<DiagnosticIssue>
    fun diagnose()
    fun reset()
}