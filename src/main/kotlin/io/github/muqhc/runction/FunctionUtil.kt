package io.github.muqhc.runction

infix fun <T,R1,R2> ((R1) -> R2).bindLeft(func: (T) -> R1): (T) -> R2 = { invoke(func(it)) }

infix fun <T,R1,R2> ((T) -> R1).bindRight(func: (R1) -> R2): (T) -> R2 = { func(invoke(it)) }

infix fun <T,R> ((T) -> R).bound(target: T): R = invoke(target)

infix fun <T,R> T.bound(func: (T) -> R): R = func(this)