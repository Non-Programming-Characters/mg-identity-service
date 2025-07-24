rootProject.name = "mg-identity-service"

listOf(
    "mg-identity-service-spring",

    "mg-identity-service-common",
    "mg-identity-service-common-jpa",

    "mg-identity-service-user",
    "mg-identity-service-user-spring-jpa-adapter",
    "mg-identity-service-user-spring-rest-adapter",

    "mg-identity-service-access-token",

    "mg-identity-service-refresh-token",
    "mg-identity-service-refresh-token-spring-jpa-adapter",
    "mg-identity-service-refresh-token-spring-rest-adapter",

    "mg-identity-service-principal",
    "mg-identity-service-principal-spring-security-adapter",

    "mg-identity-service-token",
    "mg-identity-service-nimbus-token-adapter",

    "mg-identity-service-authentication",
    "mg-identity-service-authentication-spring-security-adapter"
).forEach {
    include(it)
}

