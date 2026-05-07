import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

abstract class IdofrontExtension @Inject constructor(
    objects: ObjectFactory
) {
    abstract val setJvmToolchain: Property<Boolean>
    abstract val docsVersion: Property<String>
    abstract val minecraftVersion: Property<String>
    abstract val paperDependency: Property<String>
    abstract val jvmVersion: Property<Int>
    abstract val enableContextParameters: Property<Boolean>

    val publication = objects.newInstance(PublicationExtension::class.java)
    
    fun publication(configure: PublicationExtension.() -> Unit) {
        publication.configure()
    }
}

interface PublicationExtension {
    val publishToMineInAbyssRepo: Property<Boolean>
    val addJavaPublication: Property<Boolean>
}

/**
 * Entrypoint for getting `idofront` extension with some preconfigured defaults (conventions) for properties.
 *
 * These either manually set a default, try to read it from an `idofrontLibs` catalog, or throw a descriptive error.
 */
fun Project.getIdoExtension() = extensions.findByType(IdofrontExtension::class.java)
    ?: extensions.create("idofront", IdofrontExtension::class.java).apply {
        val idoLibs = idofrontLibsRef
        fun versionOrElse(name: String, orElse: () -> String): Provider<String> =
            provider { idoLibs?.findVersion(name)?.getOrNull()?.toString() ?: orElse() }

        docsVersion.convention(versionOrElse("shocky-docs") { "0.0.8" })
        minecraftVersion.convention(versionOrElse("minecraft") { error("Minecraft version not set, either add idofrontLibs catalog or set it in the idofront extension!") })
        paperDependency.convention(minecraftVersion.map { "io.papermc.paper:paper-api:$it" })
        jvmVersion.convention(versionOrElse("java") { "21" }.map { it.toInt() })
        enableContextParameters.convention(true)
        publication.publishToMineInAbyssRepo.convention(true)
        publication.addJavaPublication.convention(true)
    }
