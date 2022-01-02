plugins {
    kotlin("jvm") version "1.6.10"
    java
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
    signing
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

group = project.properties["group"]!! as String
version = project.properties["version"]!! as String

repositories {
    mavenCentral()
}


nexusPublishing {
    repositories {
        sonatype {
            //for Sonatype Nexus (OSSRH) which is used for The Central Repository
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            username.set(project.properties["ossrhUsername"]!! as String)
            password.set(project.properties["ossrhPassword"]!! as String)
        }
    }
}


dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks {
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    create<Jar>("dokkaJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")

        from("$buildDir/dokka/html/") {
            include("**")
        }
    }

    test {
        useJUnitPlatform()
    }
}

publishing {
    repositories {
        mavenLocal()

        maven {
            name = "debug"
            url = rootProject.uri(".debug/libraries")
        }

        maven {
            name = "OSSRH"

            credentials {
                username = project.properties["ossrhUsername"]!! as String
                password = project.properties["ossrhPassword"]!! as String
            }

            url = uri(
                if ("SNAPSHOT" in version as String) {
                    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                } else {
                    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                }
            )
        }
    }

    publications {
        create<MavenPublication>(rootProject.name) {

            groupId = project.properties["group"]!! as String
            artifactId = rootProject.name

            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["dokkaJar"])

            pom {
                name.set(rootProject.name)
                description.set("The library for your enjoyment with functions.")
                url.set("https://github.com/muqhc/${rootProject.name}")

                licenses {
                    license {
                        name.set("The Unlicense")
                        url.set("https://unlicense.org/")
                    }
                }

                developers {
                    developer {
                        id.set("muqhc")
                        name.set("Muqhc")
                        email.set("muqhc07@gmail.com")
                        url.set("https://github.com/muqhc")
                        roles.addAll("developer")
                        timezone.set("Asia/Seoul")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/muqhc/${rootProject.name}.git")
                    developerConnection.set("scm:git:ssh://github.com:muqhc/${rootProject.name}.git")
                    url.set("https://github.com/muqhc/${rootProject.name}")
                }
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        project.properties["signing.keyId"]!! as String,
        project.properties["signing.key"]!! as String,
        project.properties["signing.password"]!! as String
    )
    sign(publishing.publications[rootProject.name])
}

tasks.withType<io.github.gradlenexus.publishplugin.InitializeNexusStagingRepository> {
    shouldRunAfter(tasks.withType<Sign>())
}

