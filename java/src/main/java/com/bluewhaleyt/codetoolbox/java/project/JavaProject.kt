package com.bluewhaleyt.codetoolbox.java.project

import android.content.Context
import com.bluewhaleyt.codetoolbox.core.CodeToolboxInternalApi
import com.bluewhaleyt.codetoolbox.core.analyzer.Analyzer
import com.bluewhaleyt.codetoolbox.core.utils.writeTextIfNotExist
import com.bluewhaleyt.codetoolbox.java.JavaAnalyzer
import com.bluewhaleyt.codetoolbox.java.environment.JavaEnvironment
import com.bluewhaleyt.codetoolbox.java.file.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import java.io.File
import javax.lang.model.element.Modifier

class JavaProject(
    override val context: Context,
    override val dir: File,
    override val rootDir: File
) : JvmProject(context, dir, rootDir) {

    override val environment by lazy {
        JavaEnvironment.create(this)
    }

    override val srcDir
        get() = File(dir, "src/main/java")

    override val analyzer by lazy {
        JavaAnalyzer(this)
    }

    override fun getSourceFile(fileName: String): JavaFile? {
        return sourceFiles.map { JavaFile(this, it) }
            .find { fileName == it.name }
    }

    @OptIn(CodeToolboxInternalApi::class)
    override fun create() {
        super.create()
        File(srcDir, "Main.java").also { file ->
            val mainMethod = MethodSpec.methodBuilder("main").apply {
                addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                addParameter(Array<String>::class.java, "args")
                returns(TypeName.VOID)
            }.build()
            val mainClass = TypeSpec.classBuilder(file.nameWithoutExtension).apply {
                addModifiers(Modifier.PUBLIC)
                addMethod(mainMethod)
            }.build()
            val javaFile = environment.createJavaFile(mainClass)
            file.writeTextIfNotExist(javaFile.toString())
        }
    }

    companion object {
        fun create(context: Context, rootDir: File, name: String) = JavaProject(
            context = context,
            dir = File(rootDir, "java/projects/$name"),
            rootDir = File(rootDir, "java")
        ).also { it.create() }
    }

}