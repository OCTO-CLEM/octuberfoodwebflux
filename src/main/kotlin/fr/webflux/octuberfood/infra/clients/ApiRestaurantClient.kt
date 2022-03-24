package fr.webflux.octuberfood.infra.clients

import fr.webflux.octuberfood.domain.users.UserLocation
import fr.webflux.octuberfood.infra.clients.RestaurantsResponse.RestaurantResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Service
class ApiRestaurantClient {

    fun getRestaurants(userLocation: UserLocation): Flux<RestaurantResponse> =
        fetchRestaurants().bodyToFlux(RestaurantsResponse::class.java).flatMap { topArtistsResponse ->
            topArtistsResponse.items.toFlux().map { restaurantResponse ->
                RestaurantResponse(
                    name = restaurantResponse.name,
                    type = restaurantResponse.type,
                )
            }
        }

    fun getBestRatedRestaurants(userLocation: UserLocation): Flux<RestaurantResponse> =
        fetchBestRatingRestaurants().bodyToFlux(RestaurantsResponse::class.java).flatMap { topArtistsResponse ->
            topArtistsResponse.items.toFlux().map { restaurantResponse ->
                RestaurantResponse(
                    name = restaurantResponse.name,
                    type = restaurantResponse.type,
                )
            }
        }

    fun getDistanceBetweenRestaurantAndUser(restaurantName: String, userLocation: UserLocation) =
        fetchDistanceBetweenRestaurantAndUser().bodyToMono(DistanceBetweenRestaurantAndMeResponse::class.java).flatMap { it.result.toMono() }

    private fun fetchRestaurants(): WebClient.ResponseSpec {
        return WebClient.builder().exchangeFunction {
            Mono.just(
                ClientResponse.create(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body("""
                    {
                      "items": [
                        {
                          "name": "belle asie",
                          "type": "asiatique"
                        },
                        {
                          "name": "los kebabos",
                          "type": "kebab"
                        },
                        {
                          "name": "ramen city",
                          "type": "asiatique"
                        },
                        {
                          "name": "la masse",
                          "type": "bistrot"
                        }
                      ]
                    }
                """.trimIndent())
                .build())
        }.build().get().retrieve()
    }

    private fun fetchBestRatingRestaurants(): WebClient.ResponseSpec {
        return WebClient.builder().exchangeFunction {
            Mono.just(
                ClientResponse.create(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body("""
                    {
                      "items": [
                          {
                            "name": "mc do",
                            "type": "fast food"
                          },
                          {
                            "name": "sushi shop",
                            "type": "asiatique"
                          },
                          {
                            "name": "kfc",
                            "type": "fast food"
                          }
                      ]
                    }
                """.trimIndent())
                .build())
        }.build().get().retrieve()
    }

    private fun fetchDistanceBetweenRestaurantAndUser(): WebClient.ResponseSpec {
        return WebClient.builder().exchangeFunction {
            Mono.just(
                ClientResponse.create(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body("""
                    {
                      "result": 10
                    }
                """.trimIndent())
                .build())
        }.build().get().retrieve()
    }
}

data class RestaurantsResponse(
    val items: List<RestaurantResponse>
) {
    data class RestaurantResponse(
        val name: String,
        val type: String,
    )
}

data class DistanceBetweenRestaurantAndMeResponse(
    val result: Int
)