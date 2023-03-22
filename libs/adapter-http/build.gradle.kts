dependencies {
    implementation(project(":libs:application"))
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("io.github.resilience4j:resilience4j-spring-boot2")
}
