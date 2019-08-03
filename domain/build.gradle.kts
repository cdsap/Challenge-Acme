plugins {
    id("kotlin")

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.41")
    testImplementation("junit:junit:4.12")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-RC1")

}
repositories {
    mavenCentral()
}
