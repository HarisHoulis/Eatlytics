// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.gradle.best.practices) apply false
    alias(libs.plugins.gradle.doctor) apply false
    alias(libs.plugins.dependency.analysis) apply false
}

// Apply gradle doctor to all projects
allprojects {
    apply(plugin = "com.osacky.doctor")
}