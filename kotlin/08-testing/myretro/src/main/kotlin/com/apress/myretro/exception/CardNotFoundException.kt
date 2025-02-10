package com.apress.myretro.exception

class CardNotFoundException : RuntimeException {
    constructor() : super("Card Not Found")
    constructor(message: String?) : super(String.format("Card Not Found: {}", message))
    constructor(message: String?, cause: Throwable?) : super(String.format("Card Not Found: {}", message), cause)
}
