plugins {
    kotlin("multiplatform")
}

val libs = idofrontLibsRef ?: error("idofrontLibs catalog not found!")

repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

kotlin {
    sourceSets {
        // Junit
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.findLibrary("kotest.assertions").get())
                implementation(libs.findLibrary("kotest.property").get())
            }
        }
    }
}
