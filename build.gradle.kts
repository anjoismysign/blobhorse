plugins {
    id("java")
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.0-beta9"
}

group = "us.mytheria"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()

    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("us.mytheria:bloblib:1.698.31")
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.shadowJar {
    minimize()
    archiveClassifier.set("")
}