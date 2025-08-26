plugins {
    `kotlin-dsl`
}

group = "com.harishoulis.eatlytics.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to fetch the dependencies and is the sourceCompatibility of the project
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "eatlytics.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "eatlytics.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "eatlytics.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "eatlytics.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "eatlytics.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidHilt") {
            id = "eatlytics.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "eatlytics.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidTesting") {
            id = "eatlytics.android.testing"
            implementationClass = "AndroidTestingConventionPlugin"
        }
        register("androidLint") {
            id = "eatlytics.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("androidBenchmark") {
            id = "eatlytics.android.benchmark"
            implementationClass = "AndroidBenchmarkConventionPlugin"
        }
    }
}
