plugins {
    kotlin("jvm") version "1.3.70"
    `maven-publish`
    maven
}

group = "com.github.protocolik"
version = "1.0.0"

repositories {
    mavenLocal()
    jcenter()
    maven { setUrl("https://jitpack.io/") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api("io.netty", "netty-buffer", "4.1.47.Final")
    api("it.unimi.dsi", "fastutil", "8.3.1")
    api("com.google.code.gson", "gson", "2.8.6")
    api("com.github.protocolik", "protocolik-api", "ab211d67d1")
    api("com.github.protocolik", "protocolik-nbt", "1.0.0")
    api("com.github.protocolik", "protocolik-mojangapi", "1.0.0")
}