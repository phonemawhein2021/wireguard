@file:Suppress("UnstableApiUsage")

// အရေးကြီးဆုံးအပိုင်း- ဒါကို ဖိုင်ရဲ့ လုံးဝ ထိပ်ဆုံးမှာ ထည့်ပါ
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    // Package Name ကို providers ကနေ မယူဘဲ တိုက်ရိုက် ရေးကြည့်ပါ (သို့မဟုတ် ရှိပြီးသားအတိုင်းထားပါ)
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
            // ဒီနေရာမှာ signingConfig ကို လုံးဝ မထည့်ပါနဲ့
        }
    }

    // CPU အမျိုးအစားအလိုက် ဖိုင်ခွဲထုတ်ခြင်း
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
    // project အချင်းချင်းချိတ်ဆက်မှု
    implementation(project(":tunnel"))
    
    // လိုအပ်သော library များ
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-ktx:1.12.0")
    
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
}
