package fr.webflux.myeat.infra.clients

import fr.webflux.myeat.domain.PreOrder
import fr.webflux.myeat.infra.clients.TopDeliversResponse.TopDeliverResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class ApiDeliverClient {

    fun getMyBestDeliver(preOrder: PreOrder): Flux<TopDeliverResponse> =
        fetchTopDelivers(preOrder).bodyToFlux(TopDeliversResponse::class.java).flatMap { topArtistsResponse ->
            topArtistsResponse.items.toFlux().map { topArtistResponse ->
                TopDeliverResponse(
                    name = topArtistResponse.name,
                    timeToDeliver = topArtistResponse.timeToDeliver,
                    typeDelivery = topArtistResponse.typeDelivery
                )
            }
        }

    private fun fetchTopDelivers(preOrder: PreOrder): WebClient.ResponseSpec {
        return WebClient.builder().exchangeFunction {
            Mono.just(
                ClientResponse.create(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body("""
                    [
                      {
                        "name": "CLEM",
                        "timeToDeliver": 11,
                        "typeDelivery": "velo electrique"
                      },
                      {
                        "name": "QUEN",
                        "timeToDeliver": 23,
                        "typeDelivery": "moto"
                      },
                      {
                        "name": "ATMA",
                        "timeToDeliver": 13,
                        "typeDelivery": "voiture"
                      },
                      {
                        "name": "JEBO",
                        "timeToDeliver": 13,
                        "typeDelivery": "velo electrique"
                      }
                    ]
                """.trimIndent())
                .build())
        }.build().get().retrieve()
    }
}

data class TopDeliversResponse(
    val items: List<TopDeliverResponse>
) {
    data class TopDeliverResponse(
        val name: String,
        val timeToDeliver: Int,
        val typeDelivery: String
    )
}