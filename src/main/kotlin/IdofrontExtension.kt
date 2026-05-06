import org.gradle.api.Project
import org.gradle.api.provider.Property
import kotlin.jvm.optionals.getOrNull

interface IdofrontExtension {
    val setJvmToolchain: Property<Boolean>
    val docsVersion: Property<String>
    val minecraftVersion: Property<String>
    val paperDependency: Property<String>
    val jvmVersion: Property<Int>
}

fun Project.getIdoExtension() = extensions.findByType(IdofrontExtension::class.java)
    ?: project.extensions.create("idofront", IdofrontExtension::class.java).apply {
        val idoLibs = idofrontLibsRef
        docsVersion.convention("0.0.8")
        minecraftVersion.convention(
            provider {
                idoLibs?.findVersion("minecraft")?.getOrNull()?.toString()
                    ?: error("Minecraft version not set, either add idofrontLibs catalog or set it in the idofront extension!")
            }
        )
        paperDependency.convention(minecraftVersion.map { "io.papermc.paper:paper-api:$it" })
        jvmVersion.convention(provider {
            idoLibs?.findVersion("jvm-for-kotlin-multiplatform")?.getOrNull()?.toString()?.toInt() ?: 21
        })
    }
