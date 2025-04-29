plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.example.nebulatest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nebulatest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    room {
        schemaDirectory("$projectDir/schemas")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    android {
        testOptions {
            unitTests.all { it.useJUnitPlatform() }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlin.coroutines)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)

    implementation(libs.datastore.datastore)

    implementation(libs.koin.core)
    implementation(libs.koin.annotations)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.core.ktx)
    implementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.runner)
    testImplementation(libs.testng)
    ksp(libs.koin.compiler)

    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.gsonDependencies.gson)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.room.paging)
    androidTestImplementation(libs.room.testing)

    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    androidTestImplementation(libs.androidx.junit)
    testRuntimeOnly(libs.junit.platform)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.turbine.turbine)
    testImplementation(libs.robolectric.robolectric)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.mockitoDependency.core)
    testImplementation(libs.mockitoDependency.kotlin)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockitoDependency.inline)
    testImplementation(libs.core.testing)
    androidTestImplementation(libs.kotlin.coroutines.test)
}