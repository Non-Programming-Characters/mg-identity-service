dependencies {
    implementation(project(":mg-identity-service-common"))
    implementation(project(":mg-identity-service-principal"))

    implementation(rootProject.libs.jackson.core.core)
    implementation(rootProject.libs.jackson.core.annotations)
}