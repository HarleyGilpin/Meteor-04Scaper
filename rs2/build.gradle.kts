plugins {
    id("java")
    id("application")
}

group = "meteor"
version = "unspecified"

java{
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    disableAutoTargetJvm()
}

dependencies {
    implementation("nulled:eventbus:1.0")
}

application {
    mainClass.set("Client")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
}

tasks.withType<JavaExec> {
    workingDir = project.rootDir
}