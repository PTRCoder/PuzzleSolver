import io.freefair.gradle.plugins.compress.tasks.SevenZip

plugins {
    id("java")
    id("application")
    id("io.freefair.lombok") version "9.2.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "4.0.0"
    id("io.freefair.compress.7z") version "9.2.0"
//    id("org.checkerframework") version "1.0.2"
}

group = "PuzzleSolver"
version = "0.1.0"

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
    // Source: https://mvnrepository.com/artifact/org.slf4j/slf4j-jdk14
    runtimeOnly("org.slf4j:slf4j-jdk14:2.0.17")
    // Source: https://mvnrepository.com/artifact/org.jetbrains/annotations
    compileOnly("org.jetbrains:annotations:26.1.0")
    implementation("org.controlsfx:controlsfx:11.2.3")
}

application {
    mainClass = "puzzlesolver.Launcher"
    applicationName = "PuzzleSolver"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.graphics")
}

jlink {
    options = listOf("--strip-debug", "--compress", "zip-6", "--no-header-files", "--no-man-pages")
    launcher {
        name = "PuzzleSolver"
    }
}

tasks.register<SevenZip>("artifact") {
    dependsOn("jpackageImage")
    archiveFileName = "${application.applicationName}-$version-${javafx.platform}-artifact.zip"
    destinationDirectory = rootProject.layout.buildDirectory.dir("artifact")
    from(rootProject.layout.buildDirectory.dir("jpackage/PuzzleSolver"))

    contentCompression = org.apache.commons.compress.archivers.sevenz.SevenZMethod.LZMA2
}

tasks.test {
    useJUnitPlatform()
}

//checkerFramework {
//    version = "3.53.1"
//    checkers = listOf(
//        "org.checkerframework.checker.nullness.NullnessChecker",
//        "org.checkerframework.checker.interning.InterningChecker",
//        "org.checkerframework.checker.index.IndexChecker",
//        "org.checkerframework.checker.i18n.I18nChecker",
//        "org.checkerframework.framework.util.PurityChecker"
//        
//    )
//}