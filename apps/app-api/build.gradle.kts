dependencies {
    implementation(project(":libs:application"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation") // valid
    implementation("org.springframework.data:spring-data-commons") // Pageable
    implementation("org.springframework.boot:spring-boot-starter-actuator") // metric
    implementation("org.flywaydb:flyway-core")
    runtimeOnly(project(":libs:adapter-http")) // to get adapter-http-property.yml
    runtimeOnly(project(":libs:adapter-persistence")) // to get adapter-persistence-property.yml
    integrationImplementation("org.springframework.boot:spring-boot-starter-test")
    integrationImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    integrationImplementation("io.rest-assured:spring-mock-mvc")
    integrationRuntimeOnly("com.h2database:h2")

    integrationImplementation("com.epages:restdocs-api-spec-mockmvc")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

val appMainClassName = "io.github.ddongeee.search.SearchApiApplication"
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(appMainClassName)
    archiveClassifier.set("boot")
}
