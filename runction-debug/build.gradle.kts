subprojects {
    val main = "${rootProject.name}-main"
    val supportProject = "${rootProject.name}-${name.split("-")[1]}"
    dependencies {
        implementation( rootProject.project(main).project(supportProject) )
    }
}