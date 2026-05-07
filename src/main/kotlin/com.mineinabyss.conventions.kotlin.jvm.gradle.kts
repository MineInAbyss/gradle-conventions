plugins {
    java
    kotlin("jvm")
}

private val ido = getIdoExtension()

repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

if (ido.enableContextParameters.get()) kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
        )
    }
}

java {
    withSourcesJar()
}

if (ido.setJvmToolchain.getOrElse(true)) {
    kotlin {
        jvmToolchain(ido.jvmVersion.get())
    }
}
