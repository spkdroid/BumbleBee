package com.bumble.headline.di.component

import com.bumble.headline.di.module.NetModule
import com.bumble.headline.viewmodel.NewsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class])
interface NetworkComponent {
    fun inject(storyListViewModel: NewsListViewModel)
}