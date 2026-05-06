plugins {
    java
    id("de.eldoria.plugin-yml.paper")
}

val ido = getIdoExtension()


repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    mavenLocal()
}

dependencies {
    compileOnly(ido.paperDependency.get())
}
