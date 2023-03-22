dependencies {
    implementation(project(":libs:application"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // jpa
    implementation("org.springframework.boot:spring-boot-devtools") // h2 console 을 보기 위해
    implementation("jakarta.annotation:jakarta.annotation-api") // annotations
    implementation("jakarta.persistence:jakarta.persistence-api") // annotations
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework:spring-tx")

    implementation("org.flywaydb:flyway-core")

    implementation("com.querydsl:querydsl-jpa")
    implementation("com.querydsl:querydsl-core")

    runtimeOnly("com.h2database:h2")

    integrationImplementation("org.springframework.boot:spring-boot-starter-test")
    integrationRuntimeOnly("com.h2database:h2")
}
