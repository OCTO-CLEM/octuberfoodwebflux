package fr.webflux.myspotify.infra

import fr.webflux.myspotify.domain.Artist
import fr.webflux.myspotify.usecases.MySpotifyUseCase
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/my_spotify")
class MySpotifyController(private val mySpotifyUseCase: MySpotifyUseCase) {

    @GetMapping
    fun mySpotify(@RequestParam type: String): Flux<Artist> = mySpotifyUseCase(type)
}