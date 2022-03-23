package fr.webflux.myeat.infra

import fr.webflux.myeat.domain.Deliver
import fr.webflux.myeat.domain.PreOrder
import fr.webflux.myeat.domain.Restaurant
import fr.webflux.myeat.usecases.MyBestDeliverUseCase
import fr.webflux.myeat.usecases.RestaurantNearMeUseCase
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class MyEatController(
    private val myBestDeliverUseCase: MyBestDeliverUseCase,
    private val restaurantNearMeUseCase: RestaurantNearMeUseCase
    ) {

    @GetMapping("/my_best_deliver")
    fun mySpotify(
        @RequestParam company: String,
        @RequestParam price: Int,
        @RequestParam details: String,
        @RequestParam typeDelivery: String
    ): Mono<Deliver> = myBestDeliverUseCase(PreOrder(company, price, details.split("-"), typeDelivery))

    @GetMapping("/my_restaurant_near_me")
    fun mySpotify(): Flux<Restaurant> = restaurantNearMeUseCase()
}