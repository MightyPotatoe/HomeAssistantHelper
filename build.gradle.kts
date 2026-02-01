plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springframework.boot") version "4.0.1"
    id("pmd")
}

group = "pl.potato"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

pmd{
    toolVersion = "7.20.0"
    isIgnoreFailures = false
    ruleSetFiles = files("$rootDir/ruleset.xml")
    isConsoleOutput = false
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    implementation(libs.springBootStarterWeb)
    implementation(libs.pmd)

    testAnnotationProcessor(libs.lombok)
    testRuntimeOnly(libs.jUnitPlatformLauncher)

    testImplementation(libs.springBootStartetTest)
    testImplementation(platform(libs.jUnitBom))
    testImplementation(libs.jUnitJupiter)
}

tasks.test {
    useJUnitPlatform()
}