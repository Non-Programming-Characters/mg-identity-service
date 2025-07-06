rootProject.name = "mg-identity-service"

listOf(
    "mg-identity-service-spring",

    "mg-identity-service-common",
    "mg-identity-service-common-jpa",

    "mg-identity-service-user",
    "mg-identity-service-user-spring-jpa-adapter",
    "mg-identity-service-user-spring-rest-adapter"

).forEach {
    include(it)
}