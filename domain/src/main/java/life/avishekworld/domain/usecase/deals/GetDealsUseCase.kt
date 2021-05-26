package life.avishekworld.domain.usecase.deals

import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.repository.DealsRepository
import life.avishekworld.domain.usecase.SuspendUseCase
import life.avishekworld.domain.usecase.UseCase

class GetDealsUseCase(private val repository: DealsRepository) : UseCase {

    suspend fun run(): Deals {
        return repository.getDeals()
    }
}