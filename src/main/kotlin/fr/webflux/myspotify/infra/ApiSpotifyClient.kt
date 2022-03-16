package fr.webflux.myspotify.infra

import fr.webflux.myspotify.infra.TopArtistsResponse.TopArtistResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class ApiSpotifyClient {

    fun getUserTopItems(type: String): Flux<TopArtistResponse> =
        fetchMeTop(type).bodyToFlux(TopArtistsResponse::class.java).flatMap { topArtistsResponse ->
            topArtistsResponse.items.toFlux().map { topArtistResponse ->
                TopArtistResponse(topArtistResponse.id, topArtistResponse.name)
            }
        }

    fun getArtists(id: String): Mono<ArtistResponse> = fetchArtist(id).bodyToMono(ArtistResponse::class.java)

    private fun fetchMeTop(type: String): WebClient.ResponseSpec {
        val client = WebClient.create("https://api.spotify.com/")
        val oauthToken =
            "BQCTA5-iPqZ5Db3JqRXpyoVdWP1UJgPeM-LJzupSyhG010VzVCoXjY0raxJohu6GIZYyw9V0oRLHT0cOfx8U94p8BYVdJ4YwOv0GlkOW7oaPd-3AzOHLreBtS5RwwHBM6CLmMS8YRGtd2YDk_qVL10t4dZqsi5pt2Zv_BcecL6K3gA"
        return client
            .get()
            .uri("v1/me/top/$type")
            .header("Authorization", "Bearer $oauthToken")
            .retrieve()
    }

    private fun fetchArtist(id: String): WebClient.ResponseSpec {
        val client = WebClient.create("https://api.spotify.com/")
        val oauthToken =
            "BQBCPUeBhEK77_OhZwXd9fhrjOjwEqmBDfTHZlFpWOEGDKP21jvb2sTX4oGh0_Q8TpIxFHW1Hlojzs8Qcw8KIk5oJNJPCo1oP3UGA05bdNv6Xxt0m7BX8zdt8aM6jYJMugjW0-qewnunnNowkrjFk_eU1r_EEThQFZveeH794SYZYg"
        return client
            .get()
            .uri("v1/artists/$id")
            .header("Authorization", "Bearer $oauthToken")
            .retrieve()
    }
}

data class TopArtistsResponse(
    val items: List<TopArtistResponse>
) {
    data class TopArtistResponse(
        val id: String,
        val name: String,
    )
}

data class ArtistResponse(
    val followers: ArtistFollowersResponse,
    val popularity: Int
) {
    data class ArtistFollowersResponse(
        val total: Int,
    )
}