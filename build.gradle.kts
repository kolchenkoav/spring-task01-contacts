plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
//    id("org.mpierce.slf4j-capabilities") version "0.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework:spring-context:6.0.8")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.projectlombok:lombok:1.18.30")
    implementation("org.yaml:snakeyaml:1.30")
}

tasks.test {
    useJUnitPlatform()
}
