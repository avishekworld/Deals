package life.avishekworld.data.api.deals

import life.avishekworld.data.model.DealsResponse
import life.avishekworld.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TargetDealsApi {
    @GET("deals")
    suspend fun getDeals() : DealsResponse

    @GET("deals/{id}")
    suspend fun getDealDetails(@Path("id") productId : Int) : ProductResponse
}
