package life.avishekworld.data.api.deals

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import life.avishekworld.test_common.MockkApp
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = MockkApp::class, sdk = [28])
class TargetDealsApiTest : KoinTest {
    private val targetDealsApi : TargetDealsApi by inject()

    @Test
    fun testGetDealDetails() {
        val productResponse = targetDealsApi.getDealDetails(1)
        Thread.sleep(5000)
        assertEquals(1, productResponse.id)
    }
}