import org.gradle.jvm.application.tasks.CreateStartScripts
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    `java-library`
    application
}

repositories {
    mavenCentral()
}

dependencies {

    val versions = object {
        val hashId = "1.0.3"
        val junit5 = "5.8.0"
        val clickt = "3.2.0"
        val mordantTermColors = "1.2.1"
        val javaFaker = "1.0.2"
        val gson = "2.8.2"
    }

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.hashids:hashids:${versions.hashId}")
    testImplementation("org.junit.jupiter:junit-jupiter:${versions.junit5}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${versions.junit5}")

    // Command line handling
    implementation("com.github.ajalt.clikt:clikt:${versions.clickt}")

    // ANSI Colors
    implementation("com.github.ajalt:mordant:${versions.mordantTermColors}")

    // Fake data etc..
    implementation("com.github.javafaker:javafaker:${versions.javaFaker}")
}

val jvmTarget = 11

tasks {
    withType<JavaCompile>().all {
        sourceCompatibility = "$jvmTarget"
        targetCompatibility = "$jvmTarget"
    }
    withType<KotlinCompile>().all {
        kotlinOptions.jvmTarget = "$jvmTarget"
    }

    test {
        useJUnitPlatform {
            includeEngines("junit-jupiter")
            systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
            systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
        }
    }
}

application {
    val startupScript: CreateStartScripts by tasks.named("startScripts")
    startupScript.applicationName = "gen"
    startupScript.description = "Generates things for you."
    mainClass.set("generate.AppKt")
}
