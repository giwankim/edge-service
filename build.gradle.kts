import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "7.2.1"
}

group = "com.polarbookshop"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2025.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway-server-webflux")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("io.netty:netty-resolver-dns-native-macos") {
        artifact {
            classifier = "osx-aarch_64"
        }
    }
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName.set(project.name)
    environment.put("BP_JVM_VERSION", "24")

    docker {
        publishRegistry {
            username.set(project.findProperty("registryUsername")?.toString())
            password.set(project.findProperty("registryToken")?.toString())
            url.set(project.findProperty("registryUrl")?.toString())
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    java {
        googleJavaFormat().reorderImports(true)
    }
}
