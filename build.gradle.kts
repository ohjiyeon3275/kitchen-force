import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

ext["log4j2.version"] = "2.17.1"

val kotlinVersion = "1.6.0" // coroutine 호환을 위해 버전 다운그레이드

plugins {
    val kotlinVersion = "1.6.10"
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.asciidoctor.convert") version "1.6.0" // Gradle 7 버전에서는 버그가 있는듯함... 6 버전으로 다운그레이드..
}

group = "com"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("mysql:mysql-connector-java")
    implementation("io.github.microutils:kotlin-logging:1.12.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor") // 1
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

val snippetsDir by extra { file("build/generated-snippets") } // 변수 변경

tasks.withType<Test> {
    useJUnitPlatform()
    outputs.dir(snippetsDir)
}

/* for spring rest doc*/

val asciidoctorTask = tasks.getByName("asciidoctor") as org.asciidoctor.gradle.AsciidoctorTask
tasks.withType<org.asciidoctor.gradle.AsciidoctorTask> {
//    inputs.dir(snippetsDir)
    dependsOn(tasks.withType<Test>())
    attributes["snippets"] = snippetsDir
}

tasks.register("copyHTML", Copy::class) { // 3
    dependsOn(tasks.withType<org.asciidoctor.gradle.AsciidoctorTask>())
    from(file("build/asciidoc/html5"))
    into(file("src/main/resources/static/docs"))
}

tasks.build { // 4
    dependsOn(tasks.getByName("copyHTML"))
}

tasks.bootJar { // 5
    dependsOn(tasks.withType<org.asciidoctor.gradle.AsciidoctorTask>())
    dependsOn(tasks.getByName("copyHTML"))
}
/* for spring rest doc*/
