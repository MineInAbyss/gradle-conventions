<div align="center">

# gradle-conventions
[![Package](https://img.shields.io/maven-metadata/v?metadataUrl=https://repo.mineinabyss.com/releases/com/mineinabyss/conventions/catalog/maven-metadata.xml)](https://repo.mineinabyss.com/#/releases/com/mineinabyss/conventions/catalog)
[![Contribute](https://shields.io/badge/Contribute-e57be5?logo=github%20sponsors&style=flat&logoColor=white)](https://mineinabyss.com/contributing)
</div>

Conventions plugins for common things we need in our gradle projects (ex. configuring Kotlin, using NMS, publishing packages.) 

Note: currently this project is meant to be used via [Idofront](https://github.com/MineInAbyss/Idofront) as it needs a version catalog called `idofrontLibs` in the project for part of the configuration.

# Development

We use [gradle conventions plugins](https://docs.gradle.org/current/userguide/implementing_gradle_plugins_precompiled.html#implementing_precompiled_plugins) located under `src`. The docs are worth reading but the tldr is these are like normal `gradle.kts` files that can be applied by other project build scripts.

We also have an `idofront` extension that's shared by all the conventions to let users configure everything in one place (see `IdofrontExtension.kt`).

## Testing locally

In another project's `settings.gradle.kts` add the following to test changes to gradle conventions locally.

```kotlin
pluginManagement {
    // Assuming you cloned gradle-conventions in the same folder as this project
    includeBuild("../gradle-conventions")
}
```
