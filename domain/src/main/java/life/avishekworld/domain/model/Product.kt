package life.avishekworld.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(val id : Int,
                   val title : String,
                   val aisle : String,
                   val description : String,
                   val imageUrl : String?,
                   val regularPrice : Price,
                   val salePrice : Price?) : Parcelable {

    fun getSalePriceText() : String {
        return salePrice?.let {
            it.displayString
        } ?: regularPrice.displayString
    }
}
