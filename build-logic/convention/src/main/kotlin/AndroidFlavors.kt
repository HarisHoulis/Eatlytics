package com.harishoulis.eatlytics.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

/**
 * Configure product flavors for an Android application or library
 */
internal fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: ApplicationProductFlavor) -> Unit = {},
) {
    commonExtension.apply {
        flavorDimensions += "version"
        productFlavors {
            create("demo") {
                dimension = "version"
                flavorConfigurationBlock(this)
            }
            create("full") {
                dimension = "version"
                flavorConfigurationBlock(this)
            }
        }
    }
}
