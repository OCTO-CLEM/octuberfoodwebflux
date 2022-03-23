package fr.webflux.myeat.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MyEatRepository {
    fun getListOfRestaurant(userLocation: UserLocation, userTypeRestaurantPreference: String): Flux<Restaurant>
    fun getMyEatQuickly(preOrder: PreOrder): Mono<Deliver>
}