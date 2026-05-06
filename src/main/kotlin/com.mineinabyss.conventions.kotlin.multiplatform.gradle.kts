plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

val ido = getIdoExtension()
if (ido.setJvmToolchain.getOrElse(true)) {
    kotlin {
        jvmToolchain(ido.jvmVersion.get())
    }
}
