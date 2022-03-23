package fr.webflux.myeat.infra

import fr.webflux.myeat.domain.*
import fr.webflux.myeat.infra.clients.ApiDeliverClient
import fr.webflux.myeat.infra.clients.ApiRestaurantClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.extra.math.min

@Repository
class ApiMyEatRepository(
    private val apiDeliverClient: ApiDeliverClient,
    private val apiRestaurantClient: ApiRestaurantClient
) : MyEatRepository {

    override fun getListOfRestaurant(userLocation: UserLocation, userTypeRestaurantPreference: String): Flux<Restaurant> =
        Flux
            .zip(
                apiRestaurantClient
                    .getRestaurants(userLocation),
                apiRestaurantClient.getBestRatedRestaurants(userLocation)
            )
            .distinct()
            .filter { it.t1.type == userTypeRestaurantPreference || it.t2.type == userTypeRestaurantPreference }
            .map {
                Restaurant(
                    name = it.t1.name,
                    type = it.t1.type,
                    distanceWithMe = apiRestaurantClient.getDistanceBetweenRestaurantAndUser(it.t1.name, userLocation)
                )
            }


    override fun getMyEatQuickly(preOrder: PreOrder): Mono<Deliver> =
        apiDeliverClient
            .getMyBestDeliver(preOrder)
            .map { topDeliver ->
                Deliver(
                    name = topDeliver.name,
                    deliveryMode = topDeliver.typeDelivery,
                    timeToDeliver = topDeliver.timeToDeliver
                )
            }
            .filter { deliver -> deliver.deliveryMode == preOrder.typeDelivery }
            .min { deliver, _ -> deliver.timeToDeliver }
            .log()
}