package life.avishekworld.data.repository

import life.avishekworld.data.api.deals.TargetDealsApi
import life.avishekworld.data.cache.CacheUtil
import life.avishekworld.data.cache.DealsCache
import life.avishekworld.data.cache.DealsDetailsCache
import life.avishekworld.data.model.DealsResponse
import life.avishekworld.data.model.ProductResponse
import life.avishekworld.domain.core.Mapper
import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Product
import life.avishekworld.domain.repository.DealsRepository

class DealsRepositoryImpl(private val dealsApi: TargetDealsApi,
                          private val dealsMapper : Mapper<DealsResponse, Deals>,
                          private val dealsDetailsMapper : Mapper<ProductResponse, Product>,
                          private val dealsCache: DealsCache,
                          private val dealsDetailsCache: DealsDetailsCache,
                          private val cacheUtil: CacheUtil) : DealsRepository {

    override suspend fun getDeals(): Deals {
        return if (cacheUtil.shouldRefresh(dealsCache) || dealsCache.getDeals() == null) {
            val deals = dealsApi.getDeals().mapToDeals()
            dealsCache.saveDeals(deals)
            requireNotNull(dealsCache.getDeals())
        } else {
            requireNotNull(dealsCache.getDeals())
        }
    }

    override suspend fun getDealDetails(id: Int): Product {
        return if (cacheUtil.shouldRefresh(dealsDetailsCache) || dealsDetailsCache.getDealsDetails(id) == null) {
            val dealDetails = dealsApi.getDealDetails(id).mapToProduct()
            dealsDetailsCache.saveDealsDetails(id, dealDetails)
            requireNotNull(dealsDetailsCache.getDealsDetails(id))
        } else {
            requireNotNull(dealsDetailsCache.getDealsDetails(id))
        }
    }

    private fun DealsResponse.mapToDeals() = dealsMapper.map(this)
    private fun ProductResponse.mapToProduct() = dealsDetailsMapper.map(this)
}