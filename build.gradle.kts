plugins {
    java
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("info.solidsoft.pitest") version "1.19.0"

}

group = "br.com.alura"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

pitest {
    // Pacotes que serão mutados (sua aplicação)
    targetClasses.set(listOf("br.com.alura.screenmatch.*"))        // ← ajuste para seu pacote

    // Pacotes dos testes
    targetTests.set(listOf("test.br.com.alura.screenmatch.*"))

    // Configurações recomendadas para Spring Boot
    threads.set(4)                                   // Ajuste conforme sua máquina
    mutationEngine.set("descartes")                  // Melhor engine para código moderno

//    failWhenNoMutations.set(false)
//    skipFailingTests.set(true)

    // Exclui classes típicas do Spring que não precisam de mutação
    excludedClasses.set(
        listOf(
            "br.com.alura.screenmatch.ScreenmatchApplication",
            "**.*Application",
            "**.*Config",
            "**.*Configuration",
            "**.*Constants",
            "**.*Dto",
            "**.*Request",
            "**.*Response",
            "**.*Entity"
        )
    )

    // Evita chamadas lentas
    avoidCallsTo.set(
        listOf(
            "java.util.logging",
            "org.slf4j",
            "org.apache.log4j",
            "org.apache.commons.logging"
        )
    )

    // Relatórios
    reportDir.set(layout.buildDirectory.dir("reports/pitest"))

    // Opcional: timestamp para evitar cache
    timestampedReports.set(false)
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("io.github.cdimascio:dotenv-java:3.2.0")

    runtimeOnly("org.postgresql:postgresql")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

    pitest("eu.stamp-project:descartes:1.3.4")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}