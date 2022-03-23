package fr.webflux.myeat.infra

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MySpotifyControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `it returns list of my best artists`() {
        webTestClient
            .get()
            .uri("/my_spotify?type=artists")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath(".id").isEqualTo("")
            .jsonPath(".name").isEqualTo("Jazzy Bazz")
            .jsonPath(".followerNumbers").isEqualTo(161190)
            .jsonPath(".popularity").isEqualTo(67)
    }
}