package life.avishekworld.data.mapper

import life.avishekworld.data.model.DealsResponse
import life.avishekworld.domain.core.Mapper
import life.avishekworld.domain.model.Deals

class DealsMapper(private val productMapper: ProductMapper) : Mapper<DealsResponse, Deals> {

    override fun map(input: DealsResponse): Deals {
        return Deals(input.productList.map { productMapper.map(it) })
    }
}