android {
    // ... အခြား configuration များ ...

    buildTypes {
        release {
            isMinifyEnabled = true // Code တွေကို ချုံ့ပေးတယ်
            isShrinkResources = true // မသုံးတဲ့ Resource တွေကို ဖယ်ပေးတယ်
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            
            signingConfig = signingConfigs.getByName("release") // Sign လုပ်ဖို့ ထည့်ပေးရမယ်
        }
    }

    splits {
        abi {
            isEnable = true // ခွဲထုတ်ဖို့ ဖွင့်ပေးတာ
            reset()
            include("arm64-v8a", "armeabi-v7a") // ဒီနှစ်ခုပဲ ထုတ်မယ်
            isUniversalApk = false // ဖိုင်အားလုံးပေါင်းထားတဲ့ ဖိုင်ကြီး မထုတ်ဘူး (Size သေးစေဖို့)
        }
    }
}
