package fr.webflux.octuberfood.usecases

import fr.webflux.octuberfood.domain.OctUberFoodRepository
import fr.webflux.octuberfood.domain.Restaurant
import fr.webflux.octuberfood.domain.users.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class RestaurantNearMeUseCase(
    private val octUberFoodRepository: OctUberFoodRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flux<Restaurant> =
        Flux
            .zip(userRepository.getUserLocation(), userRepository.getUserRestaurantPreference())
            .flatMap {
                octUberFoodRepository.getListOfRestaurant(
                    userLocation = it.t1,
                    userTypeRestaurantPreference = it.t2
                )
            }
}