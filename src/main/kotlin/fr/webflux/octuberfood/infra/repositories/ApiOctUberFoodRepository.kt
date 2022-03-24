package fr.webflux.octuberfood.infra.repositories

import fr.webflux.octuberfood.domain.Deliver
import fr.webflux.octuberfood.domain.OctUberFoodRepository
import fr.webflux.octuberfood.domain.Restaurant
import fr.webflux.octuberfood.domain.users.UserLocation
import fr.webflux.octuberfood.infra.clients.ApiDeliverClient
import fr.webflux.octuberfood.infra.clients.ApiRestaurantClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.extra.math.min

@Repository
class ApiOctUberFoodRepository(
    private val apiDeliverClient: ApiDeliverClient,
    private val apiRestaurantClient: ApiRestaurantClient
) : OctUberFoodRepository {

    override fun getListOfRestaurant(userLocation: UserLocation, userTypeRestaurantPreference: String): Flux<Restaurant> =
        Flux
            .concat(
                apiRestaurantClient.getRestaurants(userLocation),
                apiRestaurantClient.getBestRatedRestaurants(userLocation)
            )
            .log()
            .filter {
                it.type == userTypeRestaurantPreference
            }
            .log()
            .flatMap { restaurantResponse ->
                apiRestaurantClient.getDistanceBetweenRestaurantAndUser(restaurantResponse.name, userLocation).map { distanceBetweenRestaurantAndUser ->
                    Restaurant(
                        name = restaurantResponse.name,
                        type = restaurantResponse.type,
                        distanceWithMe = distanceBetweenRestaurantAndUser
                    )
                }
            }
            .log()


    override fun getMyEatQuickly(typeDelivery: String, distanceBetweenRestaurantAndMe: Int): Mono<Deliver> =
        apiDeliverClient
            .getMyBestDeliver(distanceBetweenRestaurantAndMe)
            .map { topDeliver ->
                Deliver(
                    name = topDeliver.name,
                    deliveryMode = topDeliver.typeDelivery,
                    timeToDeliver = topDeliver.timeToDeliver
                )
            }
            .filter { deliver -> deliver.deliveryMode == typeDelivery }
            .min { deliver, _ -> deliver.timeToDeliver }
            .log()
}