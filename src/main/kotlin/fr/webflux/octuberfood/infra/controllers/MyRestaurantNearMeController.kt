package fr.webflux.octuberfood.infra.controllers

import fr.webflux.octuberfood.domain.Restaurant
import fr.webflux.octuberfood.usecases.RestaurantNearMeUseCase
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/my_restaurant_near_me")
class MyRestaurantNearMeController(
    private val restaurantNearMeUseCase: RestaurantNearMeUseCase
    ) {

    @GetMapping
    fun myRestaurantNearMe(): Flux<Restaurant> = restaurantNearMeUseCase()
}