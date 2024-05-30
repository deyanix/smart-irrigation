import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "eu.deyanix.pi4juart"
version = "0.0.1-SNAPSHOT"

tasks.withType<JavaCompile> {
    options.release = 21
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

plugins.withType<JavaPlugin>().configureEach {
    java {
        modularity.inferModulePath = true
    }
}

tasks.register<Copy>("copyDistribution") {
    from(configurations.default)
    from(tasks.named("jar"))
    from(layout.projectDirectory.file("assets/run.sh"))
    into(layout.buildDirectory.dir("distribution"))
}

tasks.named("build") {
    dependsOn("copyDistribution")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}

application {
    mainClass = "eu.deyanix.pi4juart.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("org.slf4j:slf4j-simple:2.0.12")
    implementation("com.pi4j:pi4j-core:2.6.0")
    implementation("com.pi4j:pi4j-plugin-gpiod:2.6.0")
    implementation("com.pi4j:pi4j-plugin-pigpio:2.6.0")
    implementation("com.pi4j:pi4j-plugin-linuxfs:2.6.0")
    implementation("com.pi4j:pi4j-plugin-raspberrypi:2.6.0")
}
