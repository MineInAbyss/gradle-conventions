plugins {
    java
    id("de.eldoria.plugin-yml.paper")
}

val ido = getIdoExtension()

repositories {
    mavenLocal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(ido.paperDependency.get())
}

paper {
    this.version = project.version.toString()
    apiVersion = "1.21"
}
