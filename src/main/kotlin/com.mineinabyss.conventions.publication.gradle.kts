plugins {
    `maven-publish`
}

publishing {
    addMineInAbyssRepo()

    // Conditional statement to support publishing on multiplatform
    if (components.findByName("java") != null) {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
            }
        }
    }
}
