plugins {
    `kotlin-dsl`
}

group = "com.harishoulis.eatlytics.buildlogic.convention"

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.spotless)
    implementation(libs.ktlint)
}
