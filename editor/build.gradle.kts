plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinPluginCompose)
}

android {
    namespace = "com.bluewhaleyt.codetoolbox.editor"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    compileOnly(libs.bundles.kotlin)
    compileOnly(libs.bundles.androidx)
    compileOnly(libs.bundles.compose)
    coreLibraryDesugaring(libs.androidDesugarJdkLibs)

    implementation(project(":core"))

    api(platform("io.github.Rosemoe.sora-editor:bom:0.23.4-43e1706-SNAPSHOT"))
    api("io.github.Rosemoe.sora-editor:editor")
    api("io.github.Rosemoe.sora-editor:language-textmate")
}