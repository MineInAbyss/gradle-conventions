plugins {
    java
    kotlin("jvm")
}

val libs = idofrontLibsRef

val jvmVersion: Int = libs.findVersion("java").get().toString().toInt()

repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

java {
    withSourcesJar()
}

val idoExtension = getIdoExtension()
if (idoExtension.setJvmToolchain.getOrElse(true)) {
    kotlin {
        jvmToolchain(jvmVersion)
    }
}
