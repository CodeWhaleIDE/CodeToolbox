plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinPluginCompose)
}

android {
    namespace = "com.bluewhaleyt.codetoolbox"

    defaultConfig {
        applicationId = "com.bluewhaleyt.codetoolbox"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    testImplementation(libs.bundles.androidxTest)
    androidTestImplementation(libs.bundles.androidxTest)
    coreLibraryDesugaring(libs.androidDesugarJdkLibs)

    implementation(project(":core"))
    implementation(project(":editor"))
    implementation(project(":java"))

    // Code Editor
    implementation(platform("io.github.Rosemoe.sora-editor:bom:0.23.2"))
    implementation("io.github.Rosemoe.sora-editor:editor")
    implementation("io.github.Rosemoe.sora-editor:language-textmate")

    // Java environment and compilation
    implementation("io.github.itsaky:nb-javac-android:17.0.0.3")

    // Kotlin environment and compilation
    implementation("com.github.Cosmic-Ide.kotlinc-android:kotlinc:2a0a6a7291")
}