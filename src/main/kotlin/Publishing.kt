import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.credentials

fun PublishingExtension.addMineInAbyssRepo() {
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
}
