package io.github.muqhc.runction

class Runction<T, R, DecoT, DecoR>(body: RunctionRaw<T, R, DecoT, DecoR>.(T) -> R) : RunctionRaw<T, R, DecoT, DecoR>(body) {

    infix fun decorateLeft(func: (DecoT) -> DecoR) =
        Runction(body).also { it.decorationFunc = func }

}

infix fun <T, R, RuncT, RuncR> ((T) -> R).decorateRight(runc: Runction<RuncT, RuncR, T, R>) =
    runc decorateLeft this

infix fun <T, R, RuncT, RuncR> ((T) -> R).decorate(runc: Runction<RuncT, RuncR, T, R>) =
    runc decorateLeft this

infix fun <T, R, DecoT, DecoR> Runction<T,R,DecoT,DecoR>.decorate(func: (DecoT) -> DecoR) =
    this decorateLeft func

fun <T, R, DecoT, DecoR> runc(body: RunctionRaw<T, R, DecoT, DecoR>.(T) -> R) =
    Runction(body)