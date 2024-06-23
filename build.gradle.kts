plugins {
    id("java")
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.data:spring-data-jpa:3.3.0")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security:3.3.0")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2") //swagger
    implementation ("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation ("org.springframework.kafka:spring-kafka:3.1.3")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.595")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")


}
tasks.test {
    useJUnitPlatform()
}