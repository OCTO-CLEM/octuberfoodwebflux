package fr.webflux.octuberfood.domain

import fr.webflux.octuberfood.domain.users.UserLocation
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OctUberFoodRepository {
    fun getListOfRestaurant(userLocation: UserLocation, userTypeRestaurantPreference: String): Flux<Restaurant>
    fun getMyEatQuickly(typeDelivery: String, distanceBetweenRestaurantAndMe: Int): Mono<Deliver>
}