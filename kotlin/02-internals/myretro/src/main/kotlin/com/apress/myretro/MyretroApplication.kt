package com.apress.myretro

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class MyretroApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(MyretroApplication::class.java, *args)
            println("More in subsequent chapters; bailing out...")
        }
    }
}
