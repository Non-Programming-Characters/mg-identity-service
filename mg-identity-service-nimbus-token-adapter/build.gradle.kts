dependencies {

    implementation(project(":mg-identity-service-token"))
    implementation(project(":mg-identity-service-principal"))
    implementation(project(":mg-identity-service-common"))

    implementation(rootProject.libs.nimbus.jose.jwt)
}