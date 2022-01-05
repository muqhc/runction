
rootProject.name = "runction"

val versions = listOf(0,1)
val debug = "${rootProject.name}-debug"

include(debug)

versions.forEach {
    val submodule = "${rootProject.name}-$it"
    include(
        submodule,
        "$debug:$submodule-debug"
    )
}