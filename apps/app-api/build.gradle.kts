dependencies {
    implementation(project(":libs:application"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly(project(":libs:adapter-http"))
    runtimeOnly(project(":libs:adapter-persistence"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

val appMainClassName = "io.github.ddongeee.search.SearchApiApplication"
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(appMainClassName)
    archiveClassifier.set("boot")
}
