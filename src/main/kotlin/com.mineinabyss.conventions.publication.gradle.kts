plugins {
    `maven-publish`
}

val idoExt = getIdoExtension()

afterEvaluate {
    publishing {
        if (idoExt.publication.publishToMineInAbyssRepo.get()) addMineInAbyssRepo()

        // Conditional statement to support publishing on multiplatform
        if (idoExt.publication.addJavaPublication.get() && components.findByName("java") != null) {
            publications {
                create<MavenPublication>("maven") {
                    from(components["java"])
                }
            }
        }
    }
}
