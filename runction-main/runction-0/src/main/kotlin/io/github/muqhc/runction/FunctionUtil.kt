package io.github.muqhc.runction

infix fun <T,R1,R2> ((T) -> R1).composite(func: (R1) -> R2): (T) -> R2 = { func(invoke(it)) }

infix fun <T,R1,R2> ((T) -> R1).compositeOn(func: R1.() -> R2): (T) -> R2 = { invoke(it).func() }

infix fun <T,R1,R2> ((R1) -> R2).compositeLeft(func: (T) -> R1): (T) -> R2 = { invoke(func(it)) }

infix fun <T,R1,R2> ((T) -> R1).compositeRight(func: (R1) -> R2): (T) -> R2 = { func(invoke(it)) }

infix fun <T,R> ((T) -> R).bind(target: T): R = invoke(target)

infix fun <T,R> T.bind(func: (T) -> R): R = func(this)

infix fun <T,R> T.bindOn(func: T.() -> R): R = this.func()

infix fun <T,R> ((T) -> R).bindLeft(target: T): R = invoke(target)

infix fun <T,R> T.bindRight(func: (T) -> R): R = func(this)