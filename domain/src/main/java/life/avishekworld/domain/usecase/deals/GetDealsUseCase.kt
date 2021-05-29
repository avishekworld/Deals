package life.avishekworld.domain.usecase.deals

import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.repository.DealsRepository
import life.avishekworld.domain.usecase.UseCase
import life.avishekworld.domain.model.Result

class GetDealsUseCase(private val repository: DealsRepository) : UseCase {

    suspend fun run(): Result<Deals> {
        return repository.getDeals()
    }
}