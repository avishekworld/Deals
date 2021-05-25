package life.avishekworld.data.cache

interface Cache {
    suspend fun getLastSavedTime() : Long
}