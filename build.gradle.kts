import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    idea
    id("io.spring.dependency-management")
    id("name.remal.sonarlint") apply false
    id("com.diffplug.spotless") apply false
    id("org.springframework.boot") apply false
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(21)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}


allprojects {
    group = "ru.otus"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    val guava: String by project
    val logback: String by project
    val slf4j: String by project
    val junit: String by project
    val assertjCore: String by project
    val mockito: String by project
    val jackson: String by project
    val postgresql: String by project
    val flyway: String by project
    val hikari: String by project
    val lombok: String by project
    val grpc: String by project
    val protobufBom: String by project

    val sockjs: String by project
    val stomp: String by project
    val bootstrap: String by project
    val springDocOpenapiUi: String by project
    val jsr305: String by project
    val r2dbcPostgresql: String by project
    val wiremock: String by project
    val nettyEpoll: String by project
    val reflections: String by project
    val assertj: String by project

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            imports {
                mavenBom(SpringBootPlugin.BOM_COORDINATES)
                mavenBom("com.google.protobuf:protobuf-bom:$protobufBom")
            }
            dependency("com.google.guava:guava:$guava")
            dependency("org.junit.jupiter:junit-jupiter-api:$junit")
            dependency("org.junit.jupiter:junit-jupiter-engine:$junit")
            dependency("org.junit.jupiter:junit-jupiter-params:$junit")
            dependency("org.assertj:assertj-core:$assertjCore")
            dependency("org.mockito:mockito-junit-jupiter:$mockito")
            dependency("org.slf4j:slf4j-api:$slf4j")
            dependency("com.fasterxml.jackson.core:jackson-core:$jackson")
            dependency("com.fasterxml.jackson.core:jackson-databind:$jackson")
            dependency("org.postgresql:postgresql:$postgresql")
            dependency("com.zaxxer:HikariCP:$hikari")
            dependency("org.projectlombok:lombok:$lombok")
            dependency("io.grpc:grpc-netty-shaded:$grpc")
            dependency("io.grpc:grpc-protobuf:$grpc")
            dependency("io.grpc:grpc-stub:$grpc")
            dependency("org.slf4j:slf4j-api:$slf4j")

            dependency("org.webjars:sockjs-client:$sockjs")
            dependency("org.webjars:stomp-websocket:$stomp")
            dependency("org.webjars:bootstrap:$bootstrap")
            dependency("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenapiUi")
            dependency("com.google.code.findbugs:jsr305:$jsr305")

            dependency("io.r2dbc:r2dbc-postgresql:$r2dbcPostgresql")
            dependency("com.github.tomakehurst:wiremock-standalone:$wiremock")
            dependency("io.netty:netty-transport-native-epoll:$nettyEpoll")
            dependency("org.reflections:reflections:$reflections")
            dependency("org.assertj:assertj-core:$assertj")





        }
    }

    configurations.all {
        resolutionStrategy {
            failOnVersionConflict()

            force("javax.servlet:servlet-api:2.4")
            force("commons-logging:commons-logging:1.1.1")
            force("commons-lang:commons-lang:2.5")
            force("org.codehaus.jackson:jackson-core-asl:1.8.8")
            force("org.codehaus.jackson:jackson-mapper-asl:1.8.3")
            force("org.codehaus.jettison:jettison:1.1")
            force("net.java.dev.jna:jna:5.8.0")
            force("com.google.errorprone:error_prone_annotations:2.7.1")
            force("org.sonarsource.analyzer-commons:sonar-analyzer-commons:2.3.0.1263")
            force("com.google.code.findbugs:jsr305:3.0.2")
            force("org.sonarsource.sslr:sslr-core:1.24.0.633")
            force("org.eclipse.platform:org.eclipse.osgi:3.18.400")
            force("org.eclipse.platform:org.eclipse.equinox.common:3.18.0")
            force("net.bytebuddy:byte-buddy:1.14.4")
            force("com.fasterxml.jackson:jackson-bom:$jackson")
            force("com.fasterxml.jackson.core:jackson-annotations:$jackson")
            force("com.fasterxml.jackson.datatype:jackson-datatype-guava:$jackson")
            force("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jackson")
            force("com.fasterxml.jackson.module:jackson-module-parameter-names:$jackson")
            force("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:$jackson")

            force("org.opentest4j:opentest4j:1.3.0")
        }
    }
}

subprojects {
    plugins.apply(JavaPlugin::class.java)
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing"))
        options.compilerArgs.add("-parameters")
    }

    apply<name.remal.gradle_plugins.sonarlint.SonarLintPlugin>()
    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            palantirJavaFormat("2.38.0")
        }
    }


    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showExceptions = true
        reports {
            junitXml.required.set(true)
            html.required.set(true)
        }
    }
}

tasks {
    val managedVersions by registering {
        doLast {
            project.extensions.getByType<DependencyManagementExtension>()
                .managedVersions
                .toSortedMap()
                .map { "${it.key}:${it.value}" }
                .forEach(::println)
        }
    }
}