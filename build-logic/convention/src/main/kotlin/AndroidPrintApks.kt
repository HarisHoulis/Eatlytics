package com.harishoulis.eatlytics.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.register

/**
 * Configure print APKs task
 */
internal fun Project.configurePrintApksTask(
    applicationExtension: ApplicationExtension,
) {
    val androidComponents = extensions.getByType<ApplicationAndroidComponentsExtension>()
    androidComponents.onVariants { variant ->
        val variantName = variant.name
        val apkFile = variant.outputs.single().outputFile
        val printApkTaskName = "print${variantName.capitalize()}Apk"
        tasks.register<TaskProvider<*>>(printApkTaskName) {
            doLast {
                println("$variantName APK: $apkFile")
            }
        }
    }
}
