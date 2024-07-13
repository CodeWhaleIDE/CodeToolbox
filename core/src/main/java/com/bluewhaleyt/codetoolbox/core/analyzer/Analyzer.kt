package com.bluewhaleyt.codetoolbox.core.analyzer

import com.bluewhaleyt.codetoolbox.core.issue.CodeIssue

interface Analyzer {
    fun getIssues(): List<CodeIssue>
    fun analyze()
    fun reset()
}