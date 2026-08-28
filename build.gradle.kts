plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "2.3.21"
}

group = "com.samtenna"
version = "0.0.1-SNAPSHOT"
description = "double_entry_ledger"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Springboot core and web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // kotlin stuff
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // db and migrations
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.flywaydb:flyway-database-postgresql")

    // testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // testcontainers for db integration testing
    testImplementation("org.testcontainers:junit-jupiter:1.21.2")
    testImplementation("org.testcontainers:postgresql:1.18.2")
    testImplementation(kotlin("test"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
