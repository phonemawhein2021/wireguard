@file:Suppress("UnstableApiUsage")

// အရေးကြီးဆုံးအပိုင်း- ဒီ plugins block က အပေါ်ဆုံးမှာ ရှိရပါမယ်
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    // ၎င်း namespace သည် သင့် app ၏ မူရင်း package name ဖြစ်ရပါမည်
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
    // အောက်ပါတို့သည် အခြေခံ library များဖြစ်သည်၊ သင့်မူရင်းဖိုင်ထဲက dependencies များကိုလည်း ဒီအောက်မှာ ဆက်ထည့်နိုင်သည်
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}
