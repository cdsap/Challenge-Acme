plugins {
    id("com.android.application")
    id("kotlin-android")
}


android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.cdsap.challenge"
        minSdkVersion(24)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFile(file("proguard-rules.pro"))
        }
    }

}


dependencies {
    implementation(project(":domain"))
    implementation(project(":repository"))
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.41")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.1")
    testImplementation("junit:junit:4.12")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-RC1")
}

