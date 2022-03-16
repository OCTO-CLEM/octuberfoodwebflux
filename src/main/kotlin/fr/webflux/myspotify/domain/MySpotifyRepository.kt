package fr.webflux.myspotify.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MySpotifyRepository {
    fun findMyBestArtists(type: String): Flux<Artist>
}