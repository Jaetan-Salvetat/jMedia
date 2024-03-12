import java.util.Properties

object AppVersion {
    const val major = 0
    const val minor = 0
    const val patch = 9
}

val properties = Properties()
val keystoreFile = project.rootProject.file("local.properties")
properties.load(keystoreFile.inputStream())

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("io.realm.kotlin")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "fr.jaetan.jmedia"
    compileSdk = 34

    defaultConfig {
        applicationId = "fr.jaetan.jmedia"
        minSdk = 26
        targetSdk = 34
        versionCode = AppVersion.major * 10000 + AppVersion.minor * 100 + AppVersion.patch
        versionName = "${AppVersion.major}.${AppVersion.minor}.${AppVersion.patch}"

        buildConfigField("String", "GITHUB_API_KEY", properties.getProperty("github.token"))
        buildConfigField("String", "THE_MOVIE_DB_API_KEY", properties.getProperty("theMovieDb.token"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("default") {
            storeFile = file("keystore.jks")
            storePassword = properties.getProperty("keystore.password")
            keyAlias = properties.getProperty("keystore.alias")
            keyPassword = properties.getProperty("keystore.key.password")
        }
    }

    buildTypes {
        create("staging") {
            isDefault = true
            isDebuggable = false
            isMinifyEnabled = false
            versionNameSuffix = "-staging"
            applicationIdSuffix = ".staging"

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("demo") {
            isMinifyEnabled = false
            versionNameSuffix = "-demo"
            applicationIdSuffix = ".demo"
            signingConfig = signingConfigs.getByName("default")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("default")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val composeVersion = "1.6.3"
    val ackpineVersion = "0.5.1"

    // Androidx
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-graphics:$composeVersion")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.navigation:navigation-compose:2.8.0-alpha04")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    // Networking
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-android:2.3.5")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-client-serialization:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("io.ktor:ktor-client-logging:2.3.5")
    implementation("io.ktor:ktor-client-okhttp:2.3.5")

    // Data
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Github auto updates
    implementation("com.github.supersu-man:apkupdater-library:v2.0.0")

    // Storage
    implementation("io.realm.kotlin:library-base:1.13.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Images
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Markdown
    implementation("com.meetup:twain:0.2.2")

    // Apk Manager
    implementation("ru.solrudev.ackpine:ackpine-core:$ackpineVersion")
    implementation("ru.solrudev.ackpine:ackpine-ktx:$ackpineVersion")

    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Others
    implementation(kotlin("reflect"))
}
