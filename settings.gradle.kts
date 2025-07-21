pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


rootProject.name = "calendar"

include("calendar-core:core-api")
include("calendar-core:core-domain")
include("calendar-storage:db-core")
include("calendar-storage:redis")
include("calendar-supports:swagger")
include("calendar-client:oauth-client")