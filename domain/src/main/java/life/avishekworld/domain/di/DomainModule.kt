package life.avishekworld.domain.di

import life.avishekworld.domain.usecase.deals.GetDealsDetailsUseCase
import life.avishekworld.domain.usecase.deals.GetDealsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetDealsUseCase(get())
    }

    factory {
        GetDealsDetailsUseCase(get())
    }
}