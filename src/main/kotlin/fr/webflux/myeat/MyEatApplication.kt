package fr.webflux.myeat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyEatApplication

fun main(args: Array<String>) {
	runApplication<MyEatApplication>(*args)
}
