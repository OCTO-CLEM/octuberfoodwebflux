package fr.webflux.myeat.domain

import reactor.core.publisher.Mono

interface UserRepository {
    fun getUserLocation(): Mono<UserLocation>
    fun getUserRestaurantPreference(): Mono<String>
}