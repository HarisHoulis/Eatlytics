package com.harishoulis.eatlytics.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.harishoulis.eatlytics.buildlogic.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("eatlytics.android.application")
            extensions.configure<ApplicationExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}
