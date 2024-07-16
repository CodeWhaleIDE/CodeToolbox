plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.bluewhaleyt.codetoolbox.core"
}

dependencies {
    implementation(libs.bundles.kotlin)
}