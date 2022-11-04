buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.13")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.1")
    }
    extra.apply {
        set("compose_ui_version", "1.1.1")

        set("grpc_version", "1.50.2")
        set("grpc_kotlin_version", "1.3.0")
        set("protobuf_version", "3.21.9")
        set("coroutines_version", "1.6.4")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version ("7.3.1") apply false
    id("com.android.library") version ("7.3.1") apply false
    id("org.jetbrains.kotlin.android") version ("1.6.10") apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    idea
}
