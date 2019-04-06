package com.wattpad.mystory.di.component

import com.wattpad.mystory.di.module.NetModule
import com.wattpad.mystory.viewmodel.StoryListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetModule::class))
interface NetworkComponent {
    fun inject(storyListViewModel: StoryListViewModel)
}