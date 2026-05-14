plugins {
    java
    id("io.papermc.paperweight.userdev")
}

val idoExtension = getIdoExtension()

//TODO we cant run this afterEvaluate, is this still reading the correct version?
dependencies {
    paperweight.paperDevBundle(idoExtension.minecraftVersion)
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
