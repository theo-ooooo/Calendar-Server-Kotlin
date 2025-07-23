tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)

    // Security
    implementation(libs.spring.boot.starter.security)
    testImplementation(libs.spring.security.test)
    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.jackson)
    runtimeOnly(libs.jjwt.impl)

    implementation(project(":calendar-core:core-domain"))
    implementation(project(":calendar-supports:swagger"))

    runtimeOnly(project(":calendar-storage:db-core"))
    runtimeOnly(project(":calendar-storage:redis"))

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(project(":calendar-storage:db-core"))
    testImplementation(project(":calendar-storage:redis"))
}