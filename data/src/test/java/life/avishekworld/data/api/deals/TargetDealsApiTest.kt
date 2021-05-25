package life.avishekworld.data.api.deals

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import life.avishekworld.test_common.MockkApp
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = MockkApp::class, sdk = [28])
class TargetDealsApiTest : KoinTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val targetDealsApi : TargetDealsApi by inject()

    @Test
    fun testGetDeals() = runBlocking {
        val dealsResponse = targetDealsApi.getDeals()
        assertFalse(dealsResponse.productList.isEmpty())
    }

    @Test
    fun testGetDealDetails() = runBlocking {
        val productResponse = targetDealsApi.getDealDetails(1)
        assertEquals(1, productResponse.id)
    }
}