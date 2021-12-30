# Runction

### _the library for your enjoyment with functions_

---
### example

---
#### runc
```kotlin
val plus = runc<
        Int,
        Runction<Int,Int,Any,Any>,
        Any, Any
        >
{ a ->
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
val plus = runc<
        Int,
        Runction<Int,Int,Int,Int>,
        Any, Any
        >
{ a ->
    runc { b ->
        decoration( a+b )
    }
}

// decoration(it) = it * it 
val plusFourThenPow = plus(4) decorateLeft { it * it }

println( plusThenPow(5) ) // ( 4 + 5 ) * ( 4 + 5 ) = 81
```
---
#### bind & runWith
```kotlin
val plusTwo = { x: Int -> x + 2 }
val plusTen = { x: Int -> x + 10 }

//val plusTwelve = plusTen( plusTwo( x ) )
val plusTwelve = plusTen bindLeft plusTwo

// println( plusTwelve( 6 ) )
plusTwelve bindRight ::println runWith 6 // 18
```
