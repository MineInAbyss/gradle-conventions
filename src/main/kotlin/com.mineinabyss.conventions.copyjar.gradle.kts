import kotlin.jvm.optionals.getOrNull

plugins {
    java
    id("com.gradleup.shadow")
    id("de.eldoria.plugin-yml.paper")
}

interface CopyJarExtension {
    val destPath: Property<String>

    val jarName: Property<String>

    val excludePlatformDependencies: Property<Boolean>
}

val pluginPath = project.findProperty("plugin_path") as? String
val copyJar = project.extensions.create<CopyJarExtension>("copyJar")
val idoLibs = idofrontLibsRef

if (pluginPath != null) {
    tasks {
        register<Copy>("copyJar") {
            description = "Copies output jar to plugins directory"
            doNotTrackState("Overwrites the plugin jar to allow for easier reloading")

            val destinationPath = copyJar.destPath.getOrElse(pluginPath)
            val outputJarName = copyJar.jarName.getOrElse("${project.name}-${project.version}.jar")

            from(findByName("shadowJar") ?: findByName("jar"))
            into(destinationPath)
            rename(".*\\.jar", outputJarName)

            doLast {
                logger.lifecycle("Copied to plugin directory: file://$destinationPath")
            }
        }

        named<DefaultTask>("build") {
            dependsOn("copyJar")
        }
    }
}

afterEvaluate {
    configurations.named("runtimeClasspath").configure {
        if (!copyJar.excludePlatformDependencies.getOrElse(true) || idoLibs == null) return@configure
        val platformDeps = idoLibs.findBundle("platform").getOrNull()?.getOrNull() ?: emptyList()
        val idofrontDeps = idoLibs.findBundle("idofront-core").getOrNull()?.getOrNull() ?: emptyList()

        val excludedDeps = (platformDeps + idofrontDeps).map { it.group to it.name }
        excludedDeps.forEach { (group, module) -> exclude(group = group, module = module) }

        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")

        logger.lifecycle("Excluded ${excludedDeps.size} platform dependencies from runtimeClasspath")
    }
}

paper {
    this.version = project.version.toString()
    apiVersion = "1.21"
}