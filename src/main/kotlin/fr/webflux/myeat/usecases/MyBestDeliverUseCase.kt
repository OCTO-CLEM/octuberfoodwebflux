package fr.webflux.myeat.usecases

import fr.webflux.myeat.domain.MyEatRepository
import fr.webflux.myeat.domain.PreOrder
import org.springframework.stereotype.Service

@Service
class MyBestDeliverUseCase(
    private val myEatRepository: MyEatRepository
) {
    operator fun invoke(preOrder: PreOrder) = myEatRepository.getMyEatQuickly(preOrder)
}