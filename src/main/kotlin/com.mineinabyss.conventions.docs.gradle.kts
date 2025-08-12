plugins {
    java apply false
}

configurations {
    create("docs")
}

val idoExt = getIdoExtension()

afterEvaluate {
    dependencies {
        "docs"("me.dvyy:shocky-docs:${idoExt.docsVersion.getOrElse("0.0.4")}")
    }
}

tasks {
    register<JavaExec>("docsGenerate") {
        classpath = configurations.getByName("docs")
        mainClass.set("me.dvyy.shocky.docs.MainKt")
        args("generate")
    }
    register<JavaExec>("docsServe") {
        classpath = configurations.getByName("docs")
        mainClass.set("me.dvyy.shocky.docs.MainKt")
        args("serve")
    }
}
