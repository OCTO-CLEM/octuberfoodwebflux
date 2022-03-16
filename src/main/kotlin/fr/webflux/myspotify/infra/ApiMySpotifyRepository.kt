package fr.webflux.myspotify.infra

import fr.webflux.myspotify.domain.Artist
import fr.webflux.myspotify.domain.MySpotifyRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class ApiMySpotifyRepository(
    private val apiSpotifyClient: ApiSpotifyClient
) : MySpotifyRepository {
    override fun findMyBestArtists(type: String): Flux<Artist> =
        apiSpotifyClient.getUserTopItems(type)
            .cache()
            .flatMap { topArtistResponse ->
                apiSpotifyClient.getArtists(topArtistResponse.id)
                    .map { artistResponse ->
                        Artist(
                            id = topArtistResponse.id,
                            name = topArtistResponse.name,
                            followerNumbers = artistResponse.followers.total,
                            popularity = artistResponse.popularity
                        )
                    }
            }.log()
}