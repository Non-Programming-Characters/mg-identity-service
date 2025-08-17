dependencies {

    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)
    implementation(rootProject.libs.springframework.spring.boot.starter.security)
    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springframework.spring.boot.starter.cache)
    implementation(rootProject.libs.springframework.spring.boot.starter.validation)
    implementation(rootProject.libs.springframework.spring.boot.starter.test)

    implementation(rootProject.libs.springframework.spring.boot.kafka)

    implementation(libs.springdoc.springdoc.openapi.starter.webmvc.ui)

    implementation(libs.nimbus.jose.jwt)

    runtimeOnly(rootProject.libs.postgresql.postgresql)

    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)


    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    listOf(
        "mg-identity-service-common",
        "mg-identity-service-common-jpa",

        "mg-identity-service-user",
        "mg-identity-service-user-spring-jpa-adapter",
        "mg-identity-service-user-spring-rest-adapter",

        "mg-identity-service-access-token",

        "mg-identity-service-refresh-token",
        "mg-identity-service-refresh-token-spring-jpa-adapter",
        "mg-identity-service-refresh-token-spring-rest-adapter",

        "mg-identity-service-principal",
        "mg-identity-service-principal-spring-security-adapter",

        "mg-identity-service-token",
        "mg-identity-service-nimbus-token-adapter",

        "mg-identity-service-authentication",
        "mg-identity-service-authentication-spring-security-adapter"
    ).forEach {
        implementation(project(":$it"))
    }
}

tasks.withType<Test> {
    failOnNoDiscoveredTests = false
}