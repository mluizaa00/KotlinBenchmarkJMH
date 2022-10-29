import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("kapt") version "1.7.20"
    id("me.champeau.gradle.jmh") version "0.5.3"
    id("io.morethan.jmhreport") version "0.9.0"
    application
}

group = "com.github.secretx33.codebench"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val javaVersion = "17"
val jmhVersion = "1.35"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.openjdk.jmh:jmh-core:$jmhVersion")
    implementation("org.openjdk.jmh:jmh-generator-bytecode:$jmhVersion")
    kapt("org.openjdk.jmh:jmh-generator-annprocess:$jmhVersion")
    implementation(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = javaVersion
    }
}

application {
    mainClass.set("com.github.secretx33.codebench.MainKt")
}

jmhReport {
    jmhResultPath = project.file("build/result.json").absolutePath
    jmhReportOutput = project.file("build/reports/jmh").absolutePath
}

tasks.register<JavaExec>("benchmarks") {
    classpath = sourceSets.getByName("test").runtimeClasspath
    mainClass.set(application.mainClass.get())
}
