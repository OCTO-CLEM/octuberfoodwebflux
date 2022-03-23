package fr.webflux.myeat.domain

import reactor.core.publisher.Mono

data class Restaurant(val name: String, val type: String, val distanceWithMe: Mono<Int>)
