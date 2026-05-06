import kotlin.io.path.createSymbolicLinkPointingTo

plugins {
    java apply false
}

repositories {
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
    maven("https://repo.mineinabyss.com/snapshots")
}

configurations {
    create("docs")
}

val idoExt = getIdoExtension()

afterEvaluate {
    dependencies {
        "docs"("me.dvyy:shocky-docs:${idoExt.docsVersion.get()}")
    }
}

tasks {
    register<JavaExec>("docsGenerate") {
        description = "Generates documentation with shocky-docs"
        classpath = configurations.getByName("docs")
        mainClass.set("me.dvyy.shocky.docs.MainKt")
        args("generate")
    }
    register<JavaExec>("docsServe") {
        description = "Serves documentation locally with automatic reloads"
        classpath = configurations.getByName("docs")
        mainClass.set("me.dvyy.shocky.docs.MainKt")
        args("serve")
    }

    register("docsCreate") {
        description = "Creates documentation for this project from a template, or does nothing if it exists."

        val docsDir = project.rootProject.layout.projectDirectory.dir("docs")
        val nameInput = project.objects.property<String>().convention(project.name)
        inputs.properties("name" to nameInput)
        outputs.dir(docsDir)

        onlyIf { !docsDir.asFile.exists() }

        doLast {
            val name = nameInput.get()
            docsDir.asFile.mkdirs()
            docsDir.file("config.yml").asFile.writeText(
                """
                #TODO Populate with project info here, update TOPBAR.md and SUMMARY.md too!
                name: $name
                siteUrl: https://docs.mineinabyss.com/$name
                """.trimIndent()
            )
            // Symlink index.md -> ../README.md
            docsDir.file("index.md").asFile.apply {
//                val readmePath = project.rootProject.file("README.md").toPath()
                toPath().createSymbolicLinkPointingTo(
                    kotlin.io.path.Path("../README.md")
                )
            }
            docsDir.file("assets").asFile.mkdirs()
            docsDir.file("SUMMARY.md").asFile.writeText(
                """
                - [Home](/)
                """.trimIndent()
            )
            docsDir.file("TOPBAR.md").asFile.writeText(
                """
                [:arrow-left: All docs](https://docs.mineinabyss.com)
                { .primary-button }

                [:brand-github: GitHub](https://github.com/MineInAbyss/$name)
                """.trimIndent()
            )
            logger.lifecycle("Created docs/config.yml with template content")
        }
    }
}
