package life.avishekworld.data.di

import com.squareup.moshi.Moshi
import life.avishekworld.data.api.deals.TargetDealsApi
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
}