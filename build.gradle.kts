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

// COPIED FROM AUTOVERSION, we can't apply the plugin to this project
fun getNextVersion(): String {
    val releaseVersion: String? = System.getenv("RELEASE_VERSION")
    val branchVersion: String? = System.getenv("BRANCH_VERSION")
    val isSnapshot = System.getenv("IS_SNAPSHOT") == "true"

    if (releaseVersion == null) return "$version-dev"

    val (major, minor) = version.toString().split(".").takeIf { it.size >= 2 }
        ?: error("Version $version does not match major.minor format!")
    fun bump(bump: String?, matchPatch: String? = null) = bump
        ?.removePrefix("v")
        ?.replace(Regex("-\\w*"), "")
        ?.split(".")
        ?.takeIf { it.size > 2 && it[0] == major && it[1] == minor && (matchPatch == null || it.getOrNull(2) == matchPatch) }
        ?.lastOrNull()?.toInt()?.plus(1)
        ?: 0

    return buildString {
        val bumpedPatch = bump(releaseVersion)
        append("$major.$minor.$bumpedPatch")
        if (isSnapshot) append("-dev.${bump(branchVersion, bumpedPatch.toString())}")
    }
}

version = getNextVersion()
