group = "com.github.m9w"

plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("org.gradle.application")
}
version = "1.0.0-Beta"

application {
    applicationName = "darkorbit-cli-client"
    mainClass.set("com.github.m9w.MainKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.code.gson", "gson", "2.13.1")
    implementation("org.jetbrains.kotlin", "kotlin-reflect", "2.1.20")
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-core", "1.8.1")
    implementation("io.netty", "netty-buffer", "4.2.0.Final")
    api("com.github.m9w", "darkorbit-protocol", "1.1.49")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    val cfg by configurations.creating {
        isCanBeResolved = true
        isCanBeConsumed = false
        extendsFrom(configurations["api"])
    }

    from(cfg.map { if (it.isDirectory) it else zipTree(it) })

    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}
