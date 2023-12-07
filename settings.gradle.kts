rootProject.name = "otus-java-hw"
include("hw01-gradle")
include("hw04-collections")

pluginManagement {
    val dependencyManagement: String by settings
    val johnrengelmanShadow: String by settings
    val sonarlint: String by settings
    val spotless: String by settings

    plugins {
        id("io.spring.dependency-management") version dependencyManagement
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
    }
}
include("hw04-collections")
