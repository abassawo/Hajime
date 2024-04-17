plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10" apply false
    id("com.codingfeline.buildkonfig") version "0.15.1" apply false

}