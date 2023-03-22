dependencies {
    implementation("org.springframework.boot:spring-boot-starter-json") // RestTemplateBuilder
    implementation("org.springframework.data:spring-data-commons") // Pageable
    implementation("org.springframework:spring-web") // RestTemplate
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // jpa
    implementation("org.springframework.boot:spring-boot-starter-validation") // valid
    implementation("org.springframework:spring-tx")

    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("jakarta.annotation:jakarta.annotation-api")// annotations

    // query dsl - QClass 생성
    implementation("com.querydsl:querydsl-core")
    annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
