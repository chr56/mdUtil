buildscript {
    val kotlinVersion: String by extra("1.6.21")
    repositories {
        mavenCentral()
        google()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://jitpack.io")
    }
}
tasks.create(name = "clean", type = Delete::class) {
    doLast { delete(rootProject.buildDir) }
}
