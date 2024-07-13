plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.bluewhaleyt.codetoolbox.kotlin"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":java"))
}