dependencies {

    implementation(project(":mg-identity-service-common-jpa"))
    implementation(project(":mg-identity-service-common"))

    implementation(project(":mg-identity-service-refresh-token"))


    implementation(rootProject.libs.springframework.spring.boot.starter.data.jpa)
}