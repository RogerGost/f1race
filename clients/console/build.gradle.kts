/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("buildlogic.java-application-conventions")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":cryptoutils"))

    implementation("com.github.freva:ascii-table:1.8.0")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("com.athaydes.rawhttp:rawhttp-core:2.6.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.0")
}

application {
    // Define the main class for the application.
    mainClass = "cat.uvic.teknos.f1race.clients.console.App"
}
