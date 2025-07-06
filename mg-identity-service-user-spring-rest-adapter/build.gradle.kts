dependencies {
    implementation(rootProject.libs.springframework.spring.boot.starter.web)

    implementation(project(":mg-identity-service-common"))
    implementation(project(":mg-identity-service-user"))
}