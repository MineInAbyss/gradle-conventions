import org.gradle.api.Project
import org.gradle.api.provider.Property

interface IdofrontExtension {
    val setJvmToolchain: Property<Boolean>
    val docsVersion: Property<String>
}

fun Project.getIdoExtension() = extensions.findByType(IdofrontExtension::class.java)
    ?: project.extensions.create("idofront", IdofrontExtension::class.java)
