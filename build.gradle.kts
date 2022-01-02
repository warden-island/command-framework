plugins {
    kotlin("jvm") version "1.5.10"
}

group = "is.warden"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    afterEvaluate {
        dependencies {
            compileOnly(kotlin("stdlib"))
            compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
        }
    }
}

