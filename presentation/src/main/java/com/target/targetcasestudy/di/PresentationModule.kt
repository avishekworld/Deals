package com.target.targetcasestudy.di

import com.target.targetcasestudy.ui.deals.viewmodel.DealVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        DealVM(get(), get(), get())
    }
}