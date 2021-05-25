package life.avishekworld.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DealsResponse(
    @Json(name = "products") val productList : List<ProductResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class ProductResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "title") val title : String,
    @Json(name = "aisle") val aisle : String,
    @Json(name = "description") val description : String,
    @Json(name = "image_url") val imageUrl : String?,
    @Json(name = "regular_price") val regularPrice : PriceResponse,
    @Json(name = "sale_price") val salePrice : PriceResponse
)

@JsonClass(generateAdapter = true)
data class PriceResponse(
    @Json(name = "amount_in_cents") val amountInCents : Int,
    @Json(name = "currency_symbol") val currencySymbol : String,
    @Json(name = "display_string") val displayString : String
)