plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

buildscript {
    repositories { gradlePluginPortal() }
    dependencies {
        classpath("org.springframework.boot:org.springframework.boot.gradle.plugin:3.4.2")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

springBoot {
    mainClass = "ru.solomka.identity.spring.MgIdentityService"
}

extra["springCloudVersion"] = "2024.0.0"
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}


subprojects {
    apply(plugin = "java-library")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.google.devtools.ksp")

    group = "ru.solomka"

    springBoot {
        mainClass = "ru.solomka.identity.spring.MgIdentityService"
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        annotationProcessor(rootProject.libs.projectlombok.lombok)

        implementation(rootProject.libs.jetbrains.annotations)
        implementation(rootProject.libs.projectlombok.lombok)
    }
}


tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "ru.solomka.identity.spring.MgIdentityService"
    }
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
    }
    useJUnitPlatform()
}
