import org.jetbrains.kotlin.util.prefixIfNot

plugins {
    `kotlin-dsl`
    `maven-publish`
    `version-catalog`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.shadowjar)
    implementation(libs.gradle.paperweight.userdev)
}

// Publish a catalog containing all our conventions plugins
catalog {
    versionCatalog {
        version("mia-conventions", version.toString())
        rootProject.file("src/main/kotlin").list()!!
            .filter { it.endsWith(".gradle.kts") }
            .forEach { name ->
                val id = name.removeSuffix(".gradle.kts")
                plugin(
                    id.removePrefix("com.mineinabyss.conventions").prefixIfNot("mia"),
                    id
                ).versionRef("mia-conventions")
            }
    }
}

publishing {
    repositories {
        maven {
            name = "mineinabyssMaven"
            val repo = "https://repo.mineinabyss.com/"
            val isSnapshot = System.getenv("IS_SNAPSHOT") == "true"
            val url = if (isSnapshot) repo + "snapshots" else repo + "releases"
            setUrl(url)
            credentials(PasswordCredentials::class)
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
            groupId = "com.mineinabyss.conventions"
            artifactId = "catalog"
        }
    }
}
