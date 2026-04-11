import io.freefair.gradle.plugins.compress.tasks.SevenZip

plugins {
    id("java")
    id("application")
    alias(libs.plugins.lombok)
    alias(libs.plugins.javafx)
    alias(libs.plugins.jlink)
    alias(libs.plugins.sevenZ)
}

group = "PuzzleSolver"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Source: https://mvnrepository.com/artifact/org.jspecify/jspecify
    compileOnly(libs.jspecify.jspecify)
    // Source: https://mvnrepository.com/artifact/org.jetbrains/annotations
    compileOnly(libs.jetbrains.annotations)

    // Source: https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation(libs.slf4j.api)
    // Source: https://mvnrepository.com/artifact/org.controlsfx/controlsfx
    implementation(libs.controlsfx.controlsfx)
    implementation(libs.jackson.toml)
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.base)

    // Source: https://mvnrepository.com/artifact/org.slf4j/slf4j-jdk14
    runtimeOnly(libs.slf4j.jdk14)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.runner)
}

application {
    mainClass = "puzzlesolver.Launcher"
    applicationName = "PuzzleSolver"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

lombok {
    version = "1.18.44"
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
    group = "build"
    description = "Build PuzzleSolver artifact. This creates a zip file from the result of the jpackageImage task"
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