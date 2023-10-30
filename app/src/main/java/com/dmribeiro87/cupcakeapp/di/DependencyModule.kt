package com.dmribeiro87.cupcakeapp.di

import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.cupcakeapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CupcakeRepository() }
    viewModel { HomeViewModel(get()) }
}
