package fr.webflux.myeat.infra

import fr.webflux.myeat.domain.UserLocation
import fr.webflux.myeat.domain.UserRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Repository
class Auth0UserRepository : UserRepository {

    override fun getUserLocation() = UserLocation(1, 2).toMono()
    override fun getUserRestaurantPreference(): Mono<String> {
        TODO("Not yet implemented")
    }
}