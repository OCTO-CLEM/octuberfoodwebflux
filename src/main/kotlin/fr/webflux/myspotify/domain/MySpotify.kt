package fr.webflux.myspotify.domain

import reactor.core.publisher.Flux

data class MySpotify(val artists: Flux<Artist>)
