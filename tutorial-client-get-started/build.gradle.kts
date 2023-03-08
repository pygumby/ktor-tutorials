val ktor_version: String by project

plugins {
    application
    kotlin("jvm") version "1.8.0"
}

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation ("ch.qos.logback:logback-classic:1.4.5")
}
