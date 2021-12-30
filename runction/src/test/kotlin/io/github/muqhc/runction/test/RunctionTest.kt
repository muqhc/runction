package io.github.muqhc.runction.test

import io.github.muqhc.runction.Runction
import io.github.muqhc.runction.*

fun main() {
    val plus = runc<Int,Runction<Int,Int,Int,Int>,Int,Int> { a ->
        val outerDecoration = { it: Int -> decoration(it) }
        runc { b ->
            decoration(
                a + b
            ){
                outerDecoration( it )
            }
        }
    }

    val plusTen = plus(10)
    val plusTwo = plus(2)

    val plusTwelve = plusTen bindLeft plusTwo

    val plusTwoThenPow = plusTwo decorateLeft { it * it }

    plusTwoThenPow bindLeft plusTwelve bindRight ::println runWith 5 //<=> println( plusTwoThenPow( plusTwelve( 5 ) )
}