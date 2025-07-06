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

    runtimeOnly(libs.postgresql.postgresql)

    implementation(rootProject.libs.springframework.spring.boot.starter.actuator)

    listOf(
        "mg-identity-service-spring",

        "mg-identity-service-common",
        "mg-identity-service-common-jpa",

        "mg-identity-service-user",
        "mg-identity-service-user-spring-jpa-adapter"
    ).forEach {
        implementation(project(":$it"))
    }
}