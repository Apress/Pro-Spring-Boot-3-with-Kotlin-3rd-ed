package com.apress.myretro

// Does not work for AOT (GraalsVm):
//
//import org.springframework.boot.SpringApplication
//import org.springframework.boot.autoconfigure.SpringBootApplication
//
//@SpringBootApplication
//class MyretroApplication {
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            SpringApplication.run(MyretroApplication::class.java, *args)
//        }
//    }
//}

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyretroApplication

fun main(args: Array<String>) {
    runApplication<MyretroApplication>(*args)
}
