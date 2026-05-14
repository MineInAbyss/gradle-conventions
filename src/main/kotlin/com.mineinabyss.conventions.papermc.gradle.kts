plugins {
    java
}

val ido = getIdoExtension()

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

afterEvaluate {
    dependencies {
        compileOnly(ido.paperDependency.get())
    }
}
