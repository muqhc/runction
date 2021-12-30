package io.github.muqhc.runction

open class RunctionRaw<T, R, DecoT, DecoR>(val body: RunctionRaw<T, R, DecoT, DecoR>.(T) -> R) : (T) -> R {
    protected lateinit var decorationFunc: (DecoT) -> DecoR

    fun decoration(p1: DecoT, onNonDecorate: (DecoT) -> DecoR = {
        @Suppress("UNCHECKED_CAST")
        it as DecoR
    }): DecoR =
        if (this::decorationFunc.isInitialized) decorationFunc(p1)
        else onNonDecorate(p1)

    override fun invoke(p1: T): R = body(p1)
}