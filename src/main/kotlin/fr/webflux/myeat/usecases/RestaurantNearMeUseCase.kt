package fr.webflux.myeat.usecases

import fr.webflux.myeat.domain.MyEatRepository
import fr.webflux.myeat.domain.PreOrder
import fr.webflux.myeat.domain.Restaurant
import fr.webflux.myeat.domain.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@Service
class RestaurantNearMeUseCase(
    private val myEatRepository: MyEatRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flux<Restaurant> = Flux
        .from(userRepository.getUserLocation())
        .flatMap {
            myEatRepository.getListOfRestaurant(it)
        }
}