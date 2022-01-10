# Runction

### _the library for your enjoyment with functions_
---

## add dependency

### Gradle Kotlin DSL

#### build.gradle.kts
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.muqhc:runction:0.3.0")
}
```
### the other ways
* [here](https://search.maven.org/artifact/io.github.muqhc/runction/0.2.0/jar)

## and use !

#### Hello.kt
```kotlin
import io.github.muqhc.runction.*
```
---
## example

---
#### runc
```kotlin
val plus = runc<Int, Runction<Int,Int,Any,Any>, Any, Any> { a ->
    runc { b ->
        a+b
    }
}

println( plus(2)(7) ) // 2 + 7 = 9

val plusTen = plus(10)

println( plusTen(4) ) // 10 + 4 = 14

```
---
#### decoration
```kotlin
val plus = runc<Int, Runction<Int,Int,Int,Int>, Any, Any> { a ->
    runc { b ->
        decoration( a+b )
    }
}

// decoration(it) = it * it 
val plusFourThenPow = plus(4) decorateLeft { it * it }

println( plusFourThenPow(5) ) // { it * it }( 4 + 5 ) = 81
```
---
#### bind & composite
```kotlin
val plusTwo = { x: Int -> x + 2 }
val plusTen = { x: Int -> x + 10 }

//val plusTwelve = { x -> plusTen( plusTwo( x ) ) }
val plusTwelve = plusTwo composite plusTen //<=> plusTen compositeLeft plusTwo

// { x -> println( plusTwelve( x ) ) }.invoke( 6 )
plusTwelve composite ::println bind 6 // output: 18
```
---
#### bindOn & compositeOn
```kotlin
val modulo = runc<Int,Runction<Int,Boolean,Boolean,Boolean>,Int,Int> { b ->
    runc { a ->
        a % b == 0
    }
}

val isOdd = modulo(2) compositeOn { not() }

0..9 bindOn
        { toList() } bindOn
        { this::filter bind isOdd } bind ::println
//output: [1, 3, 5, 7, 9]
```
---
#### boolean expression
```kotlin
(9 >= 8) <String> {
        ifTrue { "It's True!" }
        ifFalse { "It's false...." }
} bind ::println
//output: It's True!
```
