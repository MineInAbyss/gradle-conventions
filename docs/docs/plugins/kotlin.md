# Kotlin

We provide two Kotlin conventions, one for jvm only projects, one for multiplatform projects:

```kotlin
plugins {
    alias(miaConventions.plugins.mia.kotlin.jvm)
    // alias(miaConventions.plugins.mia.kotlin.multiplatform) // Choose one or the other
}
```

These will set a preferred jvmToolchain, publish source jars, and enable [context parameters](https://kotlinlang.org/docs/context-parameters.html).