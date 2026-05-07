import gradle.kotlin.dsl.accessors._9b81586b761fb2f481f7bedc1d52374d.kotlin

plugins {
    kotlin("multiplatform")
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

if (ido.setJvmToolchain.getOrElse(true)) {
    kotlin {
        jvmToolchain(ido.jvmVersion.get())
    }
}
