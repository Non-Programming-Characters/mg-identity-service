dependencies {
    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)

    implementation(project(":mg-identity-service-common"))

    implementation(project(":mg-identity-service-user"))

    implementation(project(":mg-identity-service-authentication"))

    implementation(project(":mg-identity-service-token"))
}