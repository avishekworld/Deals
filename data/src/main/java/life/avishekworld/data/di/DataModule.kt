package life.avishekworld.data.di

import com.squareup.moshi.Moshi
import life.avishekworld.data.api.deals.TargetDealsApi
import life.avishekworld.data.cache.*
import life.avishekworld.data.mapper.DealsMapper
import life.avishekworld.data.mapper.ProductMapper
import life.avishekworld.data.model.DealsResponse
import life.avishekworld.data.model.ProductResponse
import life.avishekworld.data.repository.DealsRepositoryImpl
import life.avishekworld.domain.core.Mapper
import life.avishekworld.domain.model.Deals
import life.avishekworld.domain.model.Product
import life.avishekworld.domain.repository.DealsRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

const val TARGET_API_BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(TARGET_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single<TargetDealsApi> {
        get<Retrofit>().create()
    }

    single <DealsCache>{
        DealsInMemoryCache()
    }

    single <DealsDetailsCache>{
        DealsDetailsInMemoryCache()
    }

    single {
        CacheUtil()
    }

    single {
        ProductMapper()
    }

    single {
        DealsMapper(get())
    }

    single <DealsRepository>{
        DealsRepositoryImpl(get(), get(), get(), get(), get(), get())
    }
}