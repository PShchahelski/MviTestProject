package com.android.mvi.test.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.android.mvi.test.injection.ViewModelFactory
import com.android.mvi.test.presentation.browse.BrowseContactListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class PresentationBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseContactListViewModel::class)
    abstract fun bindBrowseAccountListViewModel(viewModel: BrowseContactListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
