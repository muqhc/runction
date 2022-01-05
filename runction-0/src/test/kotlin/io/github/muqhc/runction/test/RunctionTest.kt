package io.github.muqhc.runction.test

import io.github.muqhc.runction.*

fun main() {
    val plus = runc<Int, Runction<Int,Int,Int,Int>, Int, Int>{ a ->
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

    val plusTwelve = plusTen composite plusTwo //<=> { x -> plusTwo( plusTen( x ) ) }

    val plusTwoThenPow = plusTwo decorateLeft { it * it }

    plusTwelve composite plusTwoThenPow composite ::println bind 5 //<=> println( plusTwoThenPow( plusTwelve( 5 ) )
    5 bind plusTwelve bind plusTwoThenPow bind ::println //<=> println( plusTwoThenPow( plusTwelve( 5 ) )

    val modulo = runc<Int,Runction<Int,Boolean,Boolean,Boolean>,Int,Int> { b ->
        runc { a ->
            a % b == 0
        }
    }

    val isOdd = modulo(2) compositeOn { not() }

    0..9 bindOn
            { toList() } bindOn
            { this::filter bind isOdd } bind ::println

}