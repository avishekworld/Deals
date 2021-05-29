package life.avishekworld.domain.usecase.deals

import life.avishekworld.domain.model.Product
import life.avishekworld.domain.model.Result
import life.avishekworld.domain.repository.DealsRepository
import life.avishekworld.domain.usecase.UseCase

class GetDealsDetailsUseCase(private val repository: DealsRepository) : UseCase {

    suspend fun run(id : Int): Result<Product> {
        return repository.getDealDetails(id)
    }
}