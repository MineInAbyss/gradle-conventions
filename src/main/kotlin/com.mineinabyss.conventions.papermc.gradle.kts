plugins {
    java
}

val ido = getIdoExtension()

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(ido.paperDependency.get())
}
