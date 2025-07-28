import java.net.URL
import java.util.*

tasks.register("updateIdofrontVersion") {
    doLast {
        val githubApiUrl = "https://api.github.com/repos/MineInAbyss/Idofront/releases/latest"
        val connection = URL(githubApiUrl).openConnection()
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json")

        val response = connection.inputStream.bufferedReader().use { it.readText() }
        val latestVersion = "\"tag_name\":\\s*\"v?([^\"]+)\"".toRegex().find(response)?.groupValues?.get(1)

        if (latestVersion != null) {
            val gradlePropertiesFile = project.file("gradle.properties")
            val properties = Properties()

            gradlePropertiesFile.inputStream().use { properties.load(it) }
            val currentVersion = properties.getProperty("idofrontVersion")

            if (currentVersion != latestVersion) {
                properties.setProperty("idofrontVersion", latestVersion)
                gradlePropertiesFile.outputStream().use { properties.store(it, null) }
                println("Updated idofrontVersion from $currentVersion to $latestVersion")
            } else {
                println("idofrontVersion is already up to date ($currentVersion)")
            }
        } else {
            println("Failed to retrieve the latest version from GitHub")
        }
    }
}
