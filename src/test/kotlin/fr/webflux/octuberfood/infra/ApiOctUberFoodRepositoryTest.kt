package fr.webflux.octuberfood.infra

import fr.webflux.octuberfood.domain.Restaurant
import fr.webflux.octuberfood.domain.users.UserLocation
import fr.webflux.octuberfood.infra.clients.ApiDeliverClient
import fr.webflux.octuberfood.infra.clients.ApiRestaurantClient
import fr.webflux.octuberfood.infra.clients.RestaurantsResponse.RestaurantResponse
import fr.webflux.octuberfood.infra.repositories.ApiOctUberFoodRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration

@ExtendWith(MockitoExtension::class)
class ApiOctUberFoodRepositoryTest {

    @Mock
    private lateinit var apiDeliverClient: ApiDeliverClient

    @Mock
    private lateinit var apiRestaurantClient: ApiRestaurantClient

    @InjectMocks
    private lateinit var apiMyEatRepository: ApiOctUberFoodRepository

    @Test
    fun `it returns a list of restaurant near the user location`() {
        val userLocation = UserLocation(1, 2)
        val userTypeRestaurantPreference = "asiatique"

        given(apiRestaurantClient.getRestaurants(userLocation)).willReturn(
            Flux.just(
                RestaurantResponse("belle asie", "asiatique"),
                RestaurantResponse("los kebabos", "kebab"),
                RestaurantResponse("ramen city", "asiatique"),
                RestaurantResponse("la masse", "bistrot")
            )
        )
        given(apiRestaurantClient.getBestRatedRestaurants(userLocation)).willReturn(
            Flux.just(
                RestaurantResponse("mc do", "fast food"),
                RestaurantResponse("sushi shop", "asiatique"),
                RestaurantResponse("kfc", "fast food"),
            )
        )

        given(apiRestaurantClient.getDistanceBetweenRestaurantAndUser("belle asie", userLocation)).willReturn(
            Mono.just(100)
        )
        given(apiRestaurantClient.getDistanceBetweenRestaurantAndUser("ramen city", userLocation)).willReturn(
            Mono.just(150).delayElement(Duration.ofSeconds(2L))
        )
        given(apiRestaurantClient.getDistanceBetweenRestaurantAndUser("sushi shop", userLocation)).willReturn(
            Mono.just(70)
        )

        val result = apiMyEatRepository.getListOfRestaurant(userLocation, userTypeRestaurantPreference)

        StepVerifier
            .create(result)
            .expectNext(Restaurant("belle asie", "asiatique", 100))
            .expectNext(Restaurant("sushi shop", "asiatique", 70))
            .expectNext(Restaurant("ramen city", "asiatique", 150))
            .verifyComplete()
    }

    @Test
    fun `test flux`() {
        val flux = Flux.just("Spring MVC", "Spring Boot", "Spring Web")
        StepVerifier.create(flux)
            .expectNext("Spring MVC")
            .expectNext("Spring Boot")
            .expectNext("Spring Web")
            .verifyComplete()
    }
}