package com.harishoulis.eatlytics.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.harishoulis.eatlytics.buildlogic.convention.configureKotlinKapt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
            }

            dependencies {
                "implementation"(libs.findLibrary("dagger").get())
                "kapt"(libs.findLibrary("dagger.compiler").get())
            }
        }
    }
}
