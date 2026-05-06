plugins {
    java
    kotlin("jvm")
}

val ido = getIdoExtension()

repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

java {
    withSourcesJar()
}

if (ido.setJvmToolchain.getOrElse(true)) {
    kotlin {
        jvmToolchain(ido.jvmVersion.get())
    }
}
