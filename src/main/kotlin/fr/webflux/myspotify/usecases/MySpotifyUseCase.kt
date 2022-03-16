package fr.webflux.myspotify.usecases

import fr.webflux.myspotify.domain.MySpotifyRepository
import org.springframework.stereotype.Service

@Service
class MySpotifyUseCase(
    private val mySpotifyRepository: MySpotifyRepository
) {
    operator fun invoke(type: String) = mySpotifyRepository.findMyBestArtists(type)
}