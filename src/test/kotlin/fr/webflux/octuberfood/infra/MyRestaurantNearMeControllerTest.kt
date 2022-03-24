package fr.webflux.octuberfood.infra

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MyRestaurantNearMeControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `it returns list of top restaurants for the given user`() {
        webTestClient
            .get()
            .uri("/my_restaurant_near_me")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json("""
                [{"name":"belle asie","type":"asiatique","distanceWithMe":10},{"name":"ramen city","type":"asiatique","distanceWithMe":10},{"name":"sushi shop","type":"asiatique","distanceWithMe":10}]
            """.trimIndent())
    }
}