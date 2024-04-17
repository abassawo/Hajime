import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    id("kotlinx-serialization")
    id("com.codingfeline.buildkonfig") version "0.15.1"
}

configure<BuildKonfigExtension> {
    packageName = "com.lindenlabs.hajime"

    val props = Properties()

    try {
        props.load(file("keys.properties").inputStream())
    } catch (e: Exception) {
        // keys are private and can not be comitted to git
    }

    // default config is required
    defaultConfigs {
        val apiKey: String = props["VIMEO_API_TOKEN"] as? String ?: ""
        val mapsKey: String = props["MAPS_API_KEY"] as? String ?: ""

        require(apiKey.isNotEmpty()) {
            "Register your api key from developer and place it in keys.properties as `VIMEO_API_TOKEN`"
        }

        require(mapsKey.isNotEmpty()) {
            "Register your maps key from developer and place it in keys.properties as `MAPS_API_KEY`"
        }

        buildConfigField(FieldSpec.Type.STRING, "VIMEO_API_TOKEN", apiKey)
        buildConfigField(FieldSpec.Type.STRING, "MAPS_API_KEY", mapsKey)
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting
        val ktorVersion = "2.2.4"
        val coroutinesVersion = "1.6.2"


        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-android:2.2.4")
            implementation("androidx.media3:media3-exoplayer:1.1.0")
            implementation("androidx.media3:media3-exoplayer-dash:1.1.0")
            implementation("androidx.media3:media3-ui:1.1.0")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
            implementation("io.coil-kt.coil3:coil-compose:3.0.0-alpha03")
            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha03")
            implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("io.ktor:ktor-client-auth:$ktorVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation(libs.maps.compose)

//            // Google maps for Android
//            implementation(libs.google.play.services.android.location)
//            api(libs.google.play.services.android.maps)  // api means its exposed to the pure-android app (for init)
//            // Google maps for Compose for Android
//            implementation(libs.google.maps.android.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.okhttp)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.2")
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.lindenlabs.hajime"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs(listOf(
        "src/androidMain/res",
        "src/commonMain/res"))

    defaultConfig {
        applicationId = "com.lindenlabs.hajime"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.lindenlabs.hajime"
            packageVersion = "1.0.0"
        }
    }
}
