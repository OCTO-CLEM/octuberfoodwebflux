package fr.webflux.octuberfood.domain.users

import reactor.core.publisher.Mono

interface UserRepository {
    fun getUserLocation(): Mono<UserLocation>
    fun getUserRestaurantPreference(): Mono<String>
}