package life.avishekworld.domain.model

data class Product(val id : Int,
                   val title : String,
                   val aisle : String,
                   val description : String,
                   val imageUrl : String?,
                   val regularPrice : Price,
                   val salePrice : Price?,)
