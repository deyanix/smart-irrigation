import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "eu.deyanix.pi4juart"
version = "0.0.1-SNAPSHOT"

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass = "eu.deyanix.pi4juart.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-actuator")
    implementation("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("com.pi4j:pi4j-core:2.6.0")
    implementation("com.pi4j:pi4j-plugin-gpiod:2.6.0")
    implementation("com.pi4j:pi4j-plugin-pigpio:2.6.0")
    implementation("com.pi4j:pi4j-plugin-linuxfs:2.6.0")
    implementation("com.pi4j:pi4j-plugin-raspberrypi:2.6.0")
}
