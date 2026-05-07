plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "gradle-conventions"

dependencyResolutionManagement {
    val catalogVersion: String by settings

    repositories {
        maven("https://repo.mineinabyss.com/releases")
        maven("https://repo.mineinabyss.com/snapshots")
        mavenLocal()
    }

    versionCatalogs {
        create("idofrontLibs") {
            from("com.mineinabyss:catalog:$catalogVersion")
        }
    }
}

includeBuild("docs")