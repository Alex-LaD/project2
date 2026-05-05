plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "2.25.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("com.example.projet2")
    mainClass.set("com.example.projet2.HelloApplication")
}

javafx {
    version = "21.0.6"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.testfx:testfx-junit5:4.0.18")
    testImplementation("org.testfx:openjfx-monocle:jdk-12.0.1+2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.xerial:sqlite-jdbc:3.47.1.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    modularity.inferModulePath.set(false)

    // Headless mode — uncomment for CI runs
    // systemProperty 'testfx.robot', 'glass'
    // systemProperty 'testfx.headless','true'
    // systemProperty 'prism.order', 'sw'
    // systemProperty 'prism.text', 't2k'
    // systemProperty 'java.awt.headless','true'
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}
