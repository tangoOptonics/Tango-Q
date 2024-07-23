plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.tangoplus.tangoq"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.tangoplus.tangoq"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    viewBinding{
        enable = true
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
//noinspection UseTomlInstead
dependencies {

    // test
    implementation("com.google.android.play:integrity:1.3.0")

    // 스켈레톤
    implementation("com.google.mediapipe:tasks-vision:0.20230731")


    // 외부 라이브러리
    implementation("io.github.ShawnLin013:number-picker:2.4.13")
    implementation("com.github.shuhart:StepView:v1.5.1")
    implementation("com.seosh817:circularseekbar:1.0.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation("com.github.MackHartley:RoundedProgressBar:3.0.0")
    implementation("com.airbnb.android:lottie:6.4.0")
    implementation("io.github.litao0621:nifty-slider:1.4.6")
    implementation("com.github.TomLeCollegue:ProgressBar-Library-Android-Kotlin:0.1.1")
    implementation("com.mikhaellopez:circularprogressbar:3.1.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")
    implementation("com.kizitonwose.calendar:view:2.5.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation("com.github.skydoves:balloon:1.6.4")
    implementation("com.github.skydoves:progressview:1.1.3")
    implementation("org.apache.commons:commons-math3:3.6.1")

    // api
    implementation("com.navercorp.nid:oauth:5.9.1")
    implementation("com.kakao.sdk:v2-common:2.19.0")
    implementation("com.kakao.sdk:v2-user:2.19.0")
    implementation("com.kakao.sdk:v2-auth:2.19.0")
    implementation("com.google.gms:google-services:4.4.2")
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-messaging-ktx:24.0.0")
    implementation("com.google.firebase:firebase-analytics:22.0.2")
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("androidx.security:security-crypto:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.health.connect:connect-client:1.1.0-alpha07")

    // 통신
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.android.volley:volley:1.2.1")

    // room
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-rxjava3:2.6.1")
    implementation("androidx.room:room-rxjava2:2.6.1")
    implementation("androidx.room:room-guava:2.6.1")
    implementation("androidx.room:room-testing:2.6.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.gms:play-services-fitness:21.2.0")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    annotationProcessor("android.arch.persistence.room:rxjava2:1.1.1")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:2.6.1")



    // 미디어
    val camera_version = "1.3.4"
    implementation("androidx.camera:camera-core:$camera_version")
    implementation("androidx.camera:camera-camera2:$camera_version")
    implementation("androidx.camera:camera-lifecycle:$camera_version")
    implementation("androidx.camera:camera-video:$camera_version")
    implementation("androidx.camera:camera-extensions:$camera_version")
    implementation("androidx.camera:camera-view:$camera_version")
    implementation(libs.androidx.camera.core)
    implementation("com.arthenica:ffmpeg-kit-full:4.5")

    // 내부 라이브러리
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation("androidx.fragment:fragment-ktx:1.8.1")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-core:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.19.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}