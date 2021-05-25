package life.avishekworld.data.mapper

import life.avishekworld.data.model.ProductResponse
import life.avishekworld.domain.core.Mapper
import life.avishekworld.domain.model.Price
import life.avishekworld.domain.model.Product

class ProductMapper : Mapper<ProductResponse, Product> {

    override fun map(input: ProductResponse): Product {
        return Product(input.id,
                input.title,
                input.aisle,
                input.description,
                input.imageUrl,
                Price(input.regularPrice.amountInCents,
                        input.regularPrice.currencySymbol,
                        input.regularPrice.displayString),
                if (input.salePrice == null) null
                else Price(input.salePrice.amountInCents,
                        input.salePrice.currencySymbol,
                        input.salePrice.displayString))
    }
}