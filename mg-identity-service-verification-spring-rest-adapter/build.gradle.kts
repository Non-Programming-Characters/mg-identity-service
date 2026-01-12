dependencies {
    implementation(project(":mg-identity-service-verification"))

    implementation(project(":mg-identity-service-common"))

    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)
}