import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("kapt") version "1.7.20"
    id("me.champeau.jmh") version "0.6.8"
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

jmh {
    val jmh = tasks.getByName("jmh")
    jmh.finalizedBy(tasks.jmhReport)

    val resultPath = file("${project.buildDir}/results/jmh")
    humanOutputFile.set(resultPath.resolve("humanResults.txt"))
    resultsFile.set(resultPath.resolve("results.json"))
    resultFormat.set("JSON")  // Result format type (one of CSV, JSON, NONE, SCSV, TEXT)

    jmh.doLast {
        println(resultsFile.get().asFile.readText())
    }
}

jmhReport {
    val jmhResultFile = tasks.jmh.get().resultsFile.get().asFile
    val outputFolder = jmhResultFile.parentFile.resolve("html").path
    jmhResultPath = jmhResultFile.path
    jmhReportOutput = outputFolder

    val jmhReport = tasks.getByName("jmhReport")
    jmhReport.doFirst {
        file(outputFolder).mkdirs()
    }
}
