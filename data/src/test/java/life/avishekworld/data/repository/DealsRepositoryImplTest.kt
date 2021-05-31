package life.avishekworld.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import life.avishekworld.data.api.deals.TargetDealsApi
import life.avishekworld.data.cache.CacheUtil
import life.avishekworld.data.cache.DealsCache
import life.avishekworld.data.cache.DealsDetailsCache
import life.avishekworld.data.mapper.DealsMapper
import life.avishekworld.data.mapper.ProductMapper
import life.avishekworld.data.model.DealsResponse
import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Result
import org.junit.Test
import retrofit2.HttpException
import kotlin.test.assertEquals

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
    fun testGetDealsFromApiSuccess() = runBlocking {
        val dealsResponse = mockk<DealsResponse>()
        val deals = mockk<Deals>()
        with(cacheUtil) {
            coEvery {
                shouldRefresh(any())
            } returns true
        }
        with(dealsCache) {
            coEvery {
                getDeals()
            } returns deals //Todo returns null andThen deals
            coEvery {
                saveDeals(deals)
            } returns Unit
        }
        with(targetDealsApi) {
            coEvery {
                getDeals()
            } returns dealsResponse
        }
        with(dealMapper) {
            every {
                map(dealsResponse)
            } returns deals
        }

        val receivedResult = dealRepository.getDeals()

        assertTrue(receivedResult is Result.Success<Deals>)
        assertEquals(deals, (receivedResult as Result.Success<Deals>).value)
        coVerify {
            cacheUtil.shouldRefresh(dealsCache)
            dealsCache.getDeals()
            targetDealsApi.getDeals()
            dealMapper.map(dealsResponse)
            dealsCache.saveDeals(deals)
        }
    }

    @Test
    fun testGetDealsFromApiFailure() = runBlocking {
        val dealsResponse = mockk<DealsResponse>()
        val deals = mockk<Deals>()
        val exception = mockk<HttpException>()
        with(cacheUtil) {
            coEvery {
                shouldRefresh(any())
            } returns true
        }
        with(dealsCache) {
            coEvery {
                getDeals()
            } returns deals //Todo returns null andThen deals
            coEvery {
                saveDeals(deals)
            } returns Unit
        }
        with(targetDealsApi) {
            coEvery {
                getDeals()
            } throws exception
        }
        with(dealMapper) {
            every {
                map(dealsResponse)
            } returns deals
        }

        val receivedResult = dealRepository.getDeals()

        assertTrue(receivedResult is Result.Failure<Deals>)
        assertEquals(exception, (receivedResult as Result.Failure<Deals>).error)
        coVerify {
            cacheUtil.shouldRefresh(dealsCache)
            targetDealsApi.getDeals()
        }
        coVerify (exactly = 0){
            dealMapper.map(dealsResponse)
            dealsCache.saveDeals(deals)
            dealsCache.getDeals()
        }
    }
}