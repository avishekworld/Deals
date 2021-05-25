package life.avishekworld.data.cache

import life.avishekworld.domain.model.Deals

interface DealsCache : Cache {
    suspend fun saveDeals(deals : Deals)
    suspend fun getDeals() : Deals?
}

class DealsInMemoryCache : DealsCache {
    private var deals : Deals? = null
    private var lastSavedTime : Long = 0L

    override suspend fun saveDeals(deals: Deals) {
        this.deals = deals
        lastSavedTime = System.currentTimeMillis()
    }

    override suspend fun getDeals(): Deals? {
        return deals
    }

    override suspend fun getLastSavedTime(): Long {
        return lastSavedTime
    }

}