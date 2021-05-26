package life.avishekworld.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(val amountInCents : Int,
                 val currencySymbol : String,
                 val displayString : String) : Parcelable
