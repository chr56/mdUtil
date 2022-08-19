plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

val libVersion = "0.0.1"

android {

    compileSdk = 32
    buildToolsVersion = "32.0.0"

    defaultConfig {
        minSdk = 23
        targetSdk = 32

        consumerProguardFiles.add(File("consumer-rules.pro"))
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

    afterEvaluate {
        tasks.withType(JavaCompile::class.java) {
            options.compilerArgs.apply {
                add("-Xlint:deprecation")
            }
        }
    }
}

tasks.create("sourceJar", type = Jar::class) {
    val sourceSet = android.sourceSets.getByName("main")
    from(
        sourceSet.java.srcDirs,
        sourceSet.resources.srcDirs
    )
    archiveClassifier.set("source")
}

dependencies {

    implementation("com.github.chr56:mdColor:0.0.1")

    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.core:core:1.7.0")

    implementation("com.google.android.material:material:1.4.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                artifact(tasks.getByName("sourceJar"))
                artifact(tasks.getByName("bundleReleaseAar"))
                groupId = "com.github.chr56"
                artifactId = "mdUtil"
                version = libVersion
            }
        }
    }
}
