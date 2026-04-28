@file:Suppress("UnstableApiUsage")

// ၁။ ဒီ Plugins block က အသက်ပဲ၊ ဒါမှ android ဆိုတာကို သိမှာပါ
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    // သင့် app ရဲ့ package name ကို providers ကနေ ဆွဲယူမယ်
    namespace = providers.gradleProperty("wireguardPackageName").get()
    compileSdk = 35

    defaultConfig {
        applicationId = namespace
        minSdk = 24
        targetSdk = 35
        versionCode = providers.gradleProperty("wireguardVersionCode").get().toInt()
        versionName = providers.gradleProperty("wireguardVersionName").get()
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // ၂။ arm64 နဲ့ armv7 ခွဲထုတ်တဲ့အပိုင်း
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
    // ကျန်တဲ့ implementation library များကို သင့်မူရင်းဖိုင်အတိုင်း ဒီအောက်မှာ ဆက်ထည့်ပေးပါ
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}
