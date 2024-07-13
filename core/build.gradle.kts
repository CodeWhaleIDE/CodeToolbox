plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.bluewhaleyt.codetoolbox.core"
}

//configurations {
//    "implementation" {
//        exclude(group = "com.intellij", module = "annotations")
//        exclude(group = "org.jetbrains", module = "annotations")
//    }
//}

dependencies {
    implementation(libs.bundles.kotlin)
}