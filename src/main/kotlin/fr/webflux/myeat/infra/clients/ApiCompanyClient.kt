package fr.webflux.myeat.infra.clients

import fr.webflux.myeat.domain.PreOrder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ApiCompanyClient {

    fun saveOrder(preOrder: PreOrder): Mono<String> = TODO()
}