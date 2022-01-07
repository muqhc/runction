
rootProject.name = "runction"

val versions = listOf(0,1)
val main = "${rootProject.name}-main"
val debug = "${rootProject.name}-debug"

include(main)
include(debug)

versions.forEach {
    val submodule = "${rootProject.name}-$it"
    include(
        "$main:$submodule",
        "$debug:$submodule-debug"
    )
}