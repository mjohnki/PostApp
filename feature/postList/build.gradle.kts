@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "de.johnki.postlist"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":data")))

    // statemachine
    implementation(libs.statemachine)
    implementation(libs.statemachine.coroutines)

    // compose
    implementation(libs.material3)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.lifecycle)
    implementation(libs.compose.constraintlayout)

    // logging
    implementation(libs.timber)


    // di
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    // test
    testImplementation(libs.bundles.unittest)
}