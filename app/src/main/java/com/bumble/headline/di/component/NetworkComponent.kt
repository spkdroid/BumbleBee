package com.wattpad.headlines.di.component

import com.wattpad.headlines.di.module.NetModule
import com.wattpad.headlines.viewmodel.NewsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class])
interface NetworkComponent {
    fun inject(storyListViewModel: NewsListViewModel)
}