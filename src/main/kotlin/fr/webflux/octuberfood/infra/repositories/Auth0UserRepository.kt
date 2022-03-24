package fr.webflux.octuberfood.infra.repositories

import fr.webflux.octuberfood.domain.users.UserLocation
import fr.webflux.octuberfood.domain.users.UserRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Repository
class Auth0UserRepository : UserRepository {

    override fun getUserLocation() = UserLocation(1, 2).toMono()
    override fun getUserRestaurantPreference(): Mono<String> = "asiatique".toMono()
}