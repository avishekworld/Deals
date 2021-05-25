package life.avishekworld.data.cache

import life.avishekworld.domain.model.Product

interface DealsDetailsCache : Cache {
    suspend fun saveDealsDetails(id : Int, product: Product)
    suspend fun getDealsDetails(id : Int) : Product?
}

class DealsDetailsInMemoryCache : DealsDetailsCache {
    private val dealsDetailsMap = mutableMapOf<Int, Product>()
    private var lastSavedTime : Long = 0L

    override suspend fun saveDealsDetails(id: Int, product: Product) {
        dealsDetailsMap[id] = product
        lastSavedTime = System.currentTimeMillis()
    }

    override suspend fun getDealsDetails(id: Int): Product? {
        return dealsDetailsMap[id]
    }

    override suspend fun getLastSavedTime(): Long {
        return lastSavedTime
    }
}