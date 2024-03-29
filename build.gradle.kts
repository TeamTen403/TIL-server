plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.teamten"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("com.h2database:h2")

    runtimeOnly("com.mysql:mysql-connector-j")

//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.springframework.security:spring-security-test")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

//
    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // dto validation
    implementation ("org.springframework.boot:spring-boot-starter-validation")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
