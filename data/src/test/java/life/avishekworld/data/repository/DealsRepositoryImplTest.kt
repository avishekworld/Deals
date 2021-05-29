package life.avishekworld.data.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import life.avishekworld.data.api.deals.TargetDealsApi
import life.avishekworld.data.cache.CacheUtil
import life.avishekworld.data.cache.DealsCache
import life.avishekworld.data.cache.DealsDetailsCache
import life.avishekworld.data.mapper.DealsMapper
import life.avishekworld.data.mapper.ProductMapper
import org.junit.Test

class DealsRepositoryImplTest {

    private val targetDealsApi = mockk<TargetDealsApi>()
    private val dealMapper = mockk<DealsMapper>()
    private val productMapper = mockk<ProductMapper>()
    private val dealsCache = mockk<DealsCache>()
    private val dealDetailsCache = mockk<DealsDetailsCache>()
    private val cacheUtil = mockk<CacheUtil>()

    private val dealRepository = DealsRepositoryImpl(
            targetDealsApi,
            dealMapper,
            productMapper,
            dealsCache,
            dealDetailsCache,
            cacheUtil
    )

    @Test
    fun testGetDealsFromApiSuccess() = runBlockingTest {
        with(cacheUtil) {
            coEvery {
                shouldRefresh(any())
            } returns true
        }
        with(dealsCache) {
            coEvery {
                getDeals()
            } returns null
        }
    }
}