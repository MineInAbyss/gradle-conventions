import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import kotlin.jvm.optionals.getOrNull

val Project.idofrontLibsRef: VersionCatalog? get() = extensions
    .getByType<VersionCatalogsExtension>()
    .find("idofrontLibs")
    .getOrNull()
