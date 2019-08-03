
plugins {
    id("java")
    id("kotlinx-serialization")
    id("kotlin")
}

dependencies {
    implementation(project(":domain"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.41")
    api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.1")
    testImplementation("junit:junit:4.12")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-RC1")
}
repositories {
    mavenCentral()
    jcenter()
}
