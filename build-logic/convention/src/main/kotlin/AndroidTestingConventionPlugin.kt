package com.harishoulis.eatlytics.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.harishoulis.eatlytics.buildlogic.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidTestingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "testImplementation"(kotlin("test"))
                "testImplementation"(libs.findLibrary("junit").get())
                "testImplementation"(libs.findLibrary("junit.jupiter").get())
                "testImplementation"(libs.findLibrary("junit.jupiter.api").get())
                "testImplementation"(libs.findLibrary("junit.jupiter.engine").get())
                "testImplementation"(libs.findLibrary("mockk").get())
                "testImplementation"(libs.findLibrary("strikt.core").get())
                "testImplementation"(libs.findLibrary("turbine").get())

                "androidTestImplementation"(libs.findLibrary("androidx.junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.espresso.core").get())
                "androidTestImplementation"(libs.findLibrary("androidx.compose.bom").get())
                "androidTestImplementation"(libs.findLibrary("androidx.ui.test.junit4").get())
                "androidTestImplementation"(libs.findLibrary("compose.screenshot.testing").get())
            }
        }
    }
}
