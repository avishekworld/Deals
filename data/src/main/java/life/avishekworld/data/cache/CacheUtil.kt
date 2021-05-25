package life.avishekworld.data.cache

class CacheUtil {
    private val refreshThreshold : Long = 1000 * 60 * 10

    suspend fun shouldRefresh(cache : Cache) : Boolean {
        return (System.currentTimeMillis() - cache.getLastSavedTime()) >= refreshThreshold
    }
}