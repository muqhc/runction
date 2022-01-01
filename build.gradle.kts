plugins {
    kotlin("jvm") version "1.6.10"
    java
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

group = properties["group"]!! as String
version = properties["version"]!! as String

repositories {
    mavenCentral()
}


nexusPublishing { //defaults to 'project.group'
    repositories {
        sonatype {   //custom repository name - 'sonatype' is pre-configured
            //for Sonatype Nexus (OSSRH) which is used for The Central Repository
            stagingProfileId.set("muqhc") //can reduce execution time by even 10 seconds
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
            name = "central"

            credentials.run {
                username = properties["ossrhUsername"]!! as String
                password = properties["ossrhPassword"]!! as String
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

            groupId = properties["group"]!! as String
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

