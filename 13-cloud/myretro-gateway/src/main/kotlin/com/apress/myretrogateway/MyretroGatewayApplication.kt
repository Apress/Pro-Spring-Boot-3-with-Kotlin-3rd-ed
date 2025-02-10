package com.apress.myretrogateway

// Does not work for AOT (GraalsVm):
//
//import org.springframework.boot.SpringApplication
//import org.springframework.boot.autoconfigure.SpringBootApplication
//
//@SpringBootApplication
//class MyretroGatewayApplication {
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            SpringApplication.run(MyretroGatewayApplication::class.java, *args)
//        }
//    }
//}

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyretroGatewayApplication

fun main(args: Array<String>) {
    runApplication<MyretroGatewayApplication>(*args)
}
