dependencies {
    api(libs.spring.boot.starter.oauth2.resource.server)
    implementation(libs.bundles.openfeign)

    implementation(project(":calendar-core:core-domain"))
}