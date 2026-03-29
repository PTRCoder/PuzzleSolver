plugins {
    id("java")
    id("application")
    id("io.freefair.lombok") version "9.2.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("de.infolektuell.jpackage") version "0.4.1"
    id("org.checkerframework") version "1.0.2"
}

group = "PuzzleSolver"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jspecify:jspecify:1.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.3")
    testRuntimeOnly("org.junit.platform:junit-platform-runner:1.14.3")
    // Source: https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.17")
    // Source: https://mvnrepository.com/artifact/org.slf4j/slf4j-ext
    implementation("org.slf4j:slf4j-ext:2.0.17")
    // Source: https://mvnrepository.com/artifact/org.slf4j/slf4j-jdk14
    implementation("org.slf4j:slf4j-jdk14:2.0.17")
    // Source: https://mvnrepository.com/artifact/org.jetbrains/annotations
    compileOnly("org.jetbrains:annotations:26.1.0")
    implementation("org.controlsfx:controlsfx:11.2.3")
}

application {
    mainClass = "puzzlesolver.Main"
    applicationName = "Puzzle Solver"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

javafx {
    version = "21"
    modules = listOf("javafx.base", "javafx.controls", "javafx.graphics")
}

tasks.test {
    useJUnitPlatform()
}

jpackage {
    javafx
}

checkerFramework {
    version = "3.53.1"
}