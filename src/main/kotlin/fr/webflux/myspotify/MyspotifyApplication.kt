package fr.webflux.myspotify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyspotifyApplication

fun main(args: Array<String>) {
	runApplication<MyspotifyApplication>(*args)
}
