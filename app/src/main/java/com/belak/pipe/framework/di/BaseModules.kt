package com.belak.pipe.framework.di

import com.belak.pipe.view.viewmodels.BaseActivityViewModel
import com.belak.pipe.view.viewmodels.BaseFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModules = module {
    viewModel { BaseActivityViewModel() }
    viewModel { BaseFragmentViewModel() }
}
