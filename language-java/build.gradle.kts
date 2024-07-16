plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.bluewhaleyt.codetoolbox.java_new"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":editor"))

    compileOnly("com.android.tools:r8:8.3.37")
    compileOnly("io.github.itsaky:nb-javac-android:17.0.0.3")
    compileOnly("com.github.Cosmic-Ide.kotlinc-android:kotlinc:2a0a6a7291")

    api("com.google.guava:guava:33.0.0-jre")
    api("com.squareup:javapoet:1.13.0")
    api("org.javassist:javassist:3.29.2-GA")
    api("com.android.tools.smali:smali-dexlib2:3.0.7")
    api("com.github.javaparser:javaparser-core:3.25.8")
    api("com.github.javaparser:javaparser-symbol-solver-core:3.25.8") {
        exclude(group = "com.google.guava", module = "guava")
    }
}