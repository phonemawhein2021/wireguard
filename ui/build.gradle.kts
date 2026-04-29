@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.legacy.kapt)
}

android {
    namespace = providers.gradleProperty("wireguardPackageName").get()
    compileSdk = 35

    defaultConfig {
        applicationId = namespace
        minSdk = 24
        targetSdk = 35
        versionCode = providers.gradleProperty("wireguardVersionCode").get().toInt()
        versionName = providers.gradleProperty("wireguardVersionName").get()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // signingConfig အပိုင်းကို လုံးဝ ဖြုတ်ထားရပါမည်
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("arm64-v8a", "armeabi-v7a")
            isUniversalApk = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":tunnel"))
    
    // libs.versions.toml ထဲက အမှန်အတိုင်း ပြန်ခေါ်ထားပါသည်
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.preference.ktx)
    
    coreLibraryDesugaring(libs.desugarJdkLibs)
}
