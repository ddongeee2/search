dependencies {
    implementation(project(":libs:application"))
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.flywaydb:flyway-core")
    implementation("com.querydsl:querydsl-core")
    implementation("com.querydsl:querydsl-jpa")
    runtimeOnly("com.h2database:h2")
}
