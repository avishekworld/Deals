package life.avishekworld.domain.repository

import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Product
import life.avishekworld.domain.model.Result

interface DealsRepository {
    suspend fun getDeals() : Result<Deals>
    suspend fun getDealDetails(id : Int) : Result<Product>
}