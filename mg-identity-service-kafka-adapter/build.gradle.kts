dependencies {
    implementation(rootProject.libs.springframework.spring.boot.kafka)

    implementation(project(":mg-identity-service-common"))
    implementation(project(":mg-identity-service-principal"))
    implementation(project(":mg-identity-service-user"))
}