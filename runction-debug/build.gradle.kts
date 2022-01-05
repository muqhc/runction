subprojects {
    val supportProject = "${rootProject.name}-${name.split("-")[1]}"
    dependencies {
        implementation( rootProject.project(supportProject) )
    }
}