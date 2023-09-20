import java.util.Properties

plugins {
	id("com.android.application")
	kotlin("android")
	kotlin("plugin.serialization") version "1.8.22"
}

android {
	namespace = "com.eva.androidtictactoe"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.eva.androidtictactoe"
		minSdk = 28
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}

		Properties().apply {
			rootProject.file("backend.properties").inputStream()
				.use { stream ->
					load(stream)
					buildConfigField(
						type = "String",
						name = "BASE_URI",
						value = "\"${getProperty("BASE_URI", "")}\""
					)
					buildConfigField(
						type = "Boolean",
						name = "IS_CONNECTION_SECURE",
						value = getProperty("IS_CONNECTION_SECURE", "false")
					)
				}

		}

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
		kotlinCompilerExtensionVersion = "1.4.8"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {

	implementation(libs.androidx.corektx)
	implementation(libs.androidx.lifecycle.runtime)
	implementation(libs.activity.compose)
	implementation(platform(libs.compose.bom))
	implementation(libs.compose.ui)
	implementation(libs.compose.toolingpreview)
	implementation(libs.compose.ui.graphics)
	implementation(libs.compose.material3)
	implementation(libs.compose.windowsize)
	implementation(libs.kotlinx.serialization.json)
	//navigation
	implementation(libs.navigation.compose)
	//datastore
	implementation(libs.datastore.preferences)
	// lifecycle runtime compose
	implementation(libs.lifecycle.runtime.compose)
	//ktor client
	implementation(libs.ktor.client.core)
	implementation(libs.ktor.client.okhttp)
	implementation(libs.ktor.client.content.negotiation)
	implementation(libs.ktor.client.serialization)
	implementation(libs.ktor.client.websockets)
	implementation(libs.ktor.client.logging)
	//koin
	api(libs.koin.core)
	implementation(libs.koin.android)
	implementation(libs.koin.compose)
	//test dependencies
	testImplementation(libs.junit)
	androidTestImplementation(libs.test.ext.junit)
	androidTestImplementation(platform(libs.compose.bom))
	androidTestImplementation(libs.compose.test.junit4)
	implementation(libs.compose.toolingpreview)
	debugImplementation(libs.compose.ui.tooling)
	debugImplementation(libs.compose.test.manifest)
}