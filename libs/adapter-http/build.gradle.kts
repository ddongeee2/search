dependencies {
    implementation(project(":libs:application"))
    implementation("org.springframework.boot:spring-boot-starter-json") // RestTemplateBuilder
    implementation("jakarta.annotation:jakarta.annotation-api") // annotations
    implementation("org.springframework:spring-web") // RestTemplate
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("io.github.resilience4j:resilience4j-spring-boot2")
    testImplementation("org.junit.jupiter:junit-jupiter")
    integrationImplementation("org.springframework.boot:spring-boot-starter-test")
}
