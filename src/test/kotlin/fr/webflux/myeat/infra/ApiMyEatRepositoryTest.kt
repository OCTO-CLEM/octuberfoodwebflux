package fr.webflux.myeat.infra

import fr.webflux.myeat.domain.Restaurant
import fr.webflux.myeat.domain.UserLocation
import fr.webflux.myeat.infra.clients.ApiDeliverClient
import fr.webflux.myeat.infra.clients.ApiRestaurantClient
import fr.webflux.myeat.infra.clients.RestaurantsResponse.RestaurantResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class ApiMyEatRepositoryTest {

    @Mock
    private lateinit var apiDeliverClient: ApiDeliverClient

    @Mock
    private lateinit var apiRestaurantClient: ApiRestaurantClient

    @InjectMocks
    private lateinit var apiMySpotifyRepository: ApiMyEatRepository

    @Test
    fun `it returns a list of restaurant near the user location`() {
        val userLocation = UserLocation(1, 2)
        val userTypeRestaurantPreference = "asiatique"

        given(apiRestaurantClient.getRestaurants(userLocation)).willReturn(
            Flux.just(
                RestaurantResponse("belle asie" , "asiatique"),
                RestaurantResponse("los kebabos", "kebab"),
                RestaurantResponse("laman city", "asiatique"),
            )
        )
        given(apiRestaurantClient.getRestaurants(userLocation)).willReturn(
            Flux.just(
                RestaurantResponse("mc do" , "fast food"),
                RestaurantResponse("kfc", "fast food"),
            )
        )

        given(apiRestaurantClient.getDistanceBetweenRestaurantAndUser("belle asie", userLocation)).willReturn(
            Mono.just(100)
        )
        given(apiRestaurantClient.getDistanceBetweenRestaurantAndUser("laman city", userLocation)).willReturn(
            Mono.just(150)
        )

        val result = apiMySpotifyRepository.getListOfRestaurant(userLocation, userTypeRestaurantPreference)

        StepVerifier
            .create(result)
            .expectNext(Restaurant("belle asie", "asiatique", Mono.just(100)))
            .expectNext(Restaurant("laman city", "asiatique", Mono.just(150)))
            .verifyComplete()
    }
}