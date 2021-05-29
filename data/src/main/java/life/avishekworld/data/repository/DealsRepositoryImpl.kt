package life.avishekworld.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import life.avishekworld.data.api.deals.TargetDealsApi
import life.avishekworld.data.cache.CacheUtil
import life.avishekworld.data.cache.DealsCache
import life.avishekworld.data.cache.DealsDetailsCache
import life.avishekworld.data.mapper.DealsMapper
import life.avishekworld.data.mapper.ProductMapper
import life.avishekworld.data.model.DealsResponse
import life.avishekworld.data.model.ProductResponse
import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Product
import life.avishekworld.domain.model.Result
import life.avishekworld.domain.repository.DealsRepository

class DealsRepositoryImpl(private val dealsApi: TargetDealsApi,
                          private val dealsMapper : DealsMapper,
                          private val productMapper : ProductMapper,
                          private val dealsCache: DealsCache,
                          private val dealsDetailsCache: DealsDetailsCache,
                          private val cacheUtil: CacheUtil) : DealsRepository {

    override suspend fun getDeals(): Result<Deals> = withContext(Dispatchers.IO) {
        if (cacheUtil.shouldRefresh(dealsCache) || dealsCache.getDeals() == null) {
            val deals = dealsApi.getDeals().mapToDeals()
            dealsCache.saveDeals(deals)
            Result.Success(requireNotNull(dealsCache.getDeals()))
        } else {
            Result.Success(requireNotNull(dealsCache.getDeals()))
        }
    }

    override suspend fun getDealDetails(id: Int): Result<Product> = withContext(Dispatchers.IO) {
        if (cacheUtil.shouldRefresh(dealsDetailsCache) || dealsDetailsCache.getDealsDetails(id) == null) {
            val dealDetails = dealsApi.getDealDetails(id).mapToProduct()
            dealsDetailsCache.saveDealsDetails(id, dealDetails)
            Result.Success(requireNotNull(dealsDetailsCache.getDealsDetails(id)))
        } else {
            Result.Success(requireNotNull(dealsDetailsCache.getDealsDetails(id)))
        }
    }

    private fun DealsResponse.mapToDeals() = dealsMapper.map(this)
    private fun ProductResponse.mapToProduct() = productMapper.map(this)
}