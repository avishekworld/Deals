package life.avishekworld.domain.repository

import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Product

interface DealsRepository {
    suspend fun getDeals() : Deals
    suspend fun getDealDetails(id : Int) : Product
}