allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.bundles.line.kotlin.jdsl)
    compileOnly(project(":calendar-core:core-domain"))

    runtimeOnly(libs.postgresql.connector)
    runtimeOnly(libs.h2)

    testImplementation(project(":calendar-core:core-domain"))
}