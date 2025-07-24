dependencies {

    implementation(project(":mg-identity-service-token"))
    implementation(project(":mg-identity-service-common"))
    implementation(project(":mg-identity-service-refresh-token"))

    implementation(rootProject.libs.springframework.spring.boot.starter.web)
    implementation(rootProject.libs.springdoc.springdoc.openapi.starter.webmvc.ui)
}