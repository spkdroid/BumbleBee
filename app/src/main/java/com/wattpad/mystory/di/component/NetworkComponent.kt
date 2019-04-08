package com.wattpad.mystory.di.component

import com.wattpad.mystory.di.module.NetModule
import com.wattpad.mystory.viewmodel.NewsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class])
interface NetworkComponent {
    fun inject(storyListViewModel: NewsListViewModel)
}