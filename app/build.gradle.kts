plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    idea
    id("com.google.protobuf")
}

val grpcVersion = rootProject.extra.get("grpc_version")
val grpcKotlinVersion = rootProject.extra.get("grpc_kotlin_version")
val protobufVersion = rootProject.extra.get("protobuf_version")
val coroutinesVersion = rootProject.extra.get("coroutines_version")


android {
    namespace = "gg.dsepractice.chinese.frontend.sample"
    compileSdk = 32

    defaultConfig {
        applicationId = "gg.dsepractice.chinese.frontend.sample"
        minSdk = 28
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

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
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // hacky method, should generate two kotlin stubs, one full full grpc and one for lite grpc instead.
    configurations {
        all { // You should exclude one of them not both of them
            exclude(group = "com.google.protobuf", module = "protobuf-kotlin-lite")
            exclude(group = "io.grpc", module = "grpc-protobuf-lite")
//            exclude(group = "com.google.protobuf", module = "protobuf-java")
        }
    }
}



dependencies {

    val composeUiVersion = rootProject.extra.get("compose_ui_version")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("androidx.fragment:fragment-ktx:1.3.1")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation("androidx.compose.material:material:1.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation(
        "androidx.compose.ui:ui-test-junit4:$composeUiVersion"
    )
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")

    implementation(platform("com.google.firebase:firebase-bom:31.0.2"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.3.0")
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")

    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    api("io.grpc:grpc-okhttp:${grpcVersion}")
    api("io.grpc:grpc-protobuf:${grpcVersion}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesVersion}")
    api("io.grpc:grpc-stub:${grpcVersion}")
    api("io.grpc:grpc-kotlin-stub:${grpcKotlinVersion}")
    api("com.google.protobuf:protobuf-kotlin:${protobufVersion}")
    api("com.google.protobuf:protobuf-java-util:${protobufVersion}")


    api("gg.dsepractice:api-external:0.0.6")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}