plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

val libVersion = "0.0.1"

android {

    compileSdk = 32
    buildToolsVersion = "32.0.0"

    namespace = "util.mddesign"

    defaultConfig {
        minSdk = 23
        targetSdk = 32

        consumerProguardFiles.add(File("consumer-rules.pro"))

        aarMetadata {
            minCompileSdk = 23
        }
    }
    sourceSets {
        named("main") {
            java.srcDir("scr/main/java")
            res.srcDir("scr/main/res")
        }
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles.apply {
                add(getDefaultProguardFile("proguard-android-optimize.txt"))
                add(File("proguard-rules.pro"))
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = false
    }

    publishing {
        publishing {
            singleVariant("release") {
                withSourcesJar()
            }
        }
    }

    afterEvaluate {
        tasks.withType(JavaCompile::class.java) {
            options.compilerArgs.apply {
                add("-Xlint:deprecation")
            }
        }
    }
}

dependencies {
    api("com.github.chr56:mdColor:0.0.1")
    compileOnly("androidx.appcompat:appcompat:1.4.1")
    compileOnly("com.google.android.material:material:1.4.0")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            groupId = "com.github.chr56"
            artifactId = "mdUtil"
            version = libVersion
        }
    }
}
