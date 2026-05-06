# docs

Adds gradle tasks for generating documentation with [shocky-docs](https://github.com/0ffz/shocky-docs).
Icons for docs can be found [here](https://tabler.io/icons).

## Configuration

```kotlin
plugins {
    alias(miaConventions.plugins.mia.docs)
}
```

You may optionally change the docs version, however we recommend keeping it unset to automatically update
when it's updated here.
```kotlin
idofront {
  docsVersion = "x.y.z" // Override shocky-docs version
}
```

## Tasks

| Name           | Description                                                                           |
|----------------|---------------------------------------------------------------------------------------|
| `docsCreate`   | Creates documentation for this project from a template, or does nothing if it exists. |
| `docsServe`    | Serves docs locally at [localhost:8080](http://localhost:8080)                        |
| `docsGenerate` | Builds docs for production, to the `out` folder.                                      |
