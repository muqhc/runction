package io.github.muqhc.runction

class Runction<T, R, DecoT, DecoR>(body: RunctionRaw<T, R, DecoT, DecoR>.(T) -> R) : RunctionRaw<T, R, DecoT, DecoR>(body) {

    infix fun decorateLeft(func: (DecoT) -> DecoR) =
        Runction(body).also { it.decorationFunc = func }

}

infix fun <T, R, RuncT, RuncR> ((T) -> R).decorateRight(runc: Runction<RuncT, RuncR, T, R>) =
    runc decorateLeft this

fun <T, R, DecoT, DecoR> runc(body: RunctionRaw<T, R, DecoT, DecoR>.(T) -> R) =
    Runction(body)