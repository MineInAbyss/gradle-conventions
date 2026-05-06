plugins {
    java
    id("io.papermc.paperweight.userdev")
}

val idoExtension = getIdoExtension()

dependencies {
    paperweight.paperDevBundle(idoExtension.minecraftVersion)
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
