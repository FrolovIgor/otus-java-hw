rootProject.name = "otus-java-hw"

pluginManagement {
    val dependencyManagement: String by settings
    val johnrengelmanShadow: String by settings
    val sonarlint: String by settings
    val spotless: String by settings
    val protobufVer: String by settings
    val springframeworkBoot: String by settings


    plugins {
        id("io.spring.dependency-management") version dependencyManagement
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
        id("com.google.protobuf") version protobufVer
        id("org.springframework.boot") version springframeworkBoot

    }
}
include("hw01-gradle")
include("hw04-collections")
include("hw06-annotations")
include("hw08-gc-heap")
include("hw10-aop-logging")
include("hw16-json-processor")
include("hw18-jdbc")
include("hw22-hibernate")
include("hw31-executors")
include("hw32-concurrent-collections")
include("hw33-grpc")
include("hw37-room-1408:client-service")
include("hw37-room-1408:datastore-service")
include("hw25-dependency-injection")
