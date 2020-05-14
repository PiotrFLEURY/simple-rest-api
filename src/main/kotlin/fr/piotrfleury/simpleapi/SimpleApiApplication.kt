package fr.piotrfleury.simpleapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleApiApplication

fun main(args: Array<String>) {
	runApplication<SimpleApiApplication>(*args)
}
