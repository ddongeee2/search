import com.linecorp.support.project.multi.recipe.configureByLabels

plugins {
    java
    id("org.springframework.boot") version Versions.springBoot apply false
    id("io.spring.dependency-management") version Versions.springDependencyManagementPlugin apply false
    id("com.linecorp.build-recipe-plugin") version Versions.lineRecipePlugin
    id("io.freefair.lombok") version Versions.lombokPlugin apply false

    kotlin("kapt") version Versions.kotlin apply false
    kotlin("jvm") version Versions.kotlin apply false
    kotlin("plugin.jpa") version Versions.kotlin apply false
    kotlin("plugin.spring") version Versions.kotlin apply false
}

allprojects {
    group = "io.github.ddongeee.search"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.restlet.com")
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

subprojects {
    apply(plugin = "idea")
}

configureByLabels("java") {
    apply(plugin = "org.gradle.java")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "groovy")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${Versions.springBoot}")
            mavenBom("org.jetbrains.kotlin:kotlin-bom:${Versions.kotlin}")
            mavenBom("com.google.guava:guava-bom:${Versions.guava}")
            mavenBom("com.querydsl:querydsl-bom:${Versions.querydsl}")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${Versions.springCloud}")
        }

        dependencies {
            dependency("org.apache.commons:commons-lang3:${Versions.apacheCommonsLang}")
            dependency("org.apache.commons:commons-collections4:${Versions.apacheCommonsCollections}")
            dependency("org.mapstruct:mapstruct:${Versions.mapstruct}")
            dependency("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")
            dependency("com.fasterxml.jackson.core:jackson-databind:${Versions.jacksonCore}")
            dependency("com.querydsl:querydsl-core:${Versions.querydsl}")
            dependency("com.querydsl:querydsl-jpa:${Versions.querydsl}")
            dependency("com.querydsl:querydsl-apt:${Versions.querydsl}")
        }
    }

    dependencies {
        val implementation by configurations
        val annotationProcessor by configurations

        val testImplementation by configurations

        implementation("com.google.guava:guava")

        implementation("org.apache.commons:commons-lang3")
        implementation("org.apache.commons:commons-collections4")
        implementation("org.mapstruct:mapstruct")
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
        implementation("io.github.resilience4j:resilience4j-spring-boot2")

        annotationProcessor("org.mapstruct:mapstruct-processor")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.spockframework:spock-core:${Versions.spock}")
        testImplementation("org.spockframework:spock-spring:${Versions.spock}")
    }
}

configureByLabels("boot") {
    apply(plugin = "org.springframework.boot")

    tasks.getByName<Jar>("jar") {
        enabled = false
    }

    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = true
        archiveClassifier.set("boot")
    }
}

configureByLabels("library") {
    apply(plugin = "java-library")

    tasks.getByName<Jar>("jar") {
        enabled = true
    }
}