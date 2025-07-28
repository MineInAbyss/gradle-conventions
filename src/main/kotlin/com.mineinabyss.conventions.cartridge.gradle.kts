plugins {
    java
}

val libs = idofrontLibsRef

repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    mavenLocal()
}

dependencies {
    compileOnly(libs.findLibrary("minecraft-papermc").get())
}

tasks {
    processResources {
        // work around IDEA-296490
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        copySpec {
            filesMatching(setOf("plugin.yml", "paper-plugin.yml")) {
                expand(mutableMapOf("plugin_version" to version))
            }
        }
    }
}
