package fr.webflux.myspotify.infra

import fr.webflux.myspotify.domain.Artist
import fr.webflux.myspotify.infra.TopArtistsResponse.TopArtistResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration

@ExtendWith(MockitoExtension::class)
class ApiMySpotifyRepositoryTest {

    @Mock
    private lateinit var apiSpotifyClient: ApiSpotifyClient

    @InjectMocks
    private lateinit var apiMySpotifyRepository: ApiMySpotifyRepository

    @Test
    fun `it gets details from other api and then returns my best artists`() {
        given(apiSpotifyClient.getUserTopItems("artists")).willReturn(
            Flux.just(
                TopArtistResponse("123456789", "Nekfeu"),
                TopArtistResponse("101121214", "Alpha Wann"),
                TopArtistResponse("1516171819", "Freeze Corleone"),
            )
        )
        given(apiSpotifyClient.getArtists("123456789")).willReturn(
            Mono.just(ArtistResponse(ArtistResponse.ArtistFollowersResponse(100000), 100))
        )
        given(apiSpotifyClient.getArtists("101121214")).willReturn(
            Mono.just(ArtistResponse(ArtistResponse.ArtistFollowersResponse(90000), 90))
        )
        given(apiSpotifyClient.getArtists("1516171819")).willReturn(
            Mono.just(ArtistResponse(ArtistResponse.ArtistFollowersResponse(95000), 95))
        )

        val result = apiMySpotifyRepository.findMyBestArtists("artists")

        StepVerifier
            .create(result)
            .expectNext(Artist("123456789", "Nekfeu", 100000, 100))
            .expectNext(Artist("101121214", "Alpha Wann", 90000, 90))
            .expectNext(Artist("1516171819", "Freeze Corleone", 95000, 95))
            .verifyComplete()
    }

    @Test
    fun `it gets details from other api and then returns my best artists with delay`() {
        given(apiSpotifyClient.getUserTopItems("artists")).willReturn(
            Flux.just(
                TopArtistResponse("123456789", "Nekfeu"),
                TopArtistResponse("101121214", "Alpha Wann"),
                TopArtistResponse("1516171819", "Freeze Corleone"),
            )
        )
        given(apiSpotifyClient.getArtists("123456789")).willReturn(
            Mono.just(ArtistResponse(ArtistResponse.ArtistFollowersResponse(100000), 100)).delayElement(Duration.ofSeconds(3L))
        )
        given(apiSpotifyClient.getArtists("101121214")).willReturn(
            Mono.just(ArtistResponse(ArtistResponse.ArtistFollowersResponse(90000), 90))
        )
        given(apiSpotifyClient.getArtists("1516171819")).willReturn(
            Mono.just(ArtistResponse(ArtistResponse.ArtistFollowersResponse(95000), 95))
        )

        val result = apiMySpotifyRepository.findMyBestArtists("artists")

        StepVerifier
            .create(result)
            .expectNext(Artist("101121214", "Alpha Wann", 90000, 90))
            .expectNext(Artist("1516171819", "Freeze Corleone", 95000, 95))
            .expectNext(Artist("123456789", "Nekfeu", 100000, 100))
            .verifyComplete()
    }
}