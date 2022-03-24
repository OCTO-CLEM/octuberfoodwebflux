package fr.webflux.octuberfood

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import java.time.Duration
import kotlin.random.Random

@SpringBootApplication
class OctUberFoodApplication {
	@Bean
	fun routeRating(ratingsHandler: RatingsHandler) = router {
		(GET("ratings") and accept(MediaType.TEXT_EVENT_STREAM)).invoke {
			ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(ratingsHandler.fetchRatings())
		}
	}
}

@Component
class RatingsHandler {

	private val ratingScores = listOf("1/5", "2/5", "3/5", "4/5", "5/5")

	fun myRestaurantNearMe(): Flux<DeliverAndScore> =
		Flux
			.generate<DeliverAndScore> {
				it.next(
					DeliverAndScore(
						List(16) { (('a'..'z') + ('A'..'Z') + ('0'..'9')).random() }.joinToString(""),
						ratingScores[Random.nextInt(ratingScores.size)]
					)
				)
			}
			.delayElements(Duration.ofSeconds(2L))
			.log()

	fun fetchRatings() = myRestaurantNearMe()
}

data class DeliverAndScore(val name: String, val rating: String)

fun main(args: Array<String>) {
	runApplication<OctUberFoodApplication>(*args)
}
