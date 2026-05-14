plugins {
    kotlin("multiplatform")
}


repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

private val ido = getIdoExtension()

afterEvaluate {
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
}