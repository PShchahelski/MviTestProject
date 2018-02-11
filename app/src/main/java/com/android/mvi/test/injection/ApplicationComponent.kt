package com.android.mvi.test.injection

import android.app.Application
import com.android.mvi.test.App
import com.android.mvi.test.injection.module.ActivityBindingModule
import com.android.mvi.test.injection.module.ApplicationBindingModule
import com.android.mvi.test.injection.module.DataModule
import com.android.mvi.test.injection.module.PresentationBindingModule
import com.android.mvi.test.injection.module.ThreadingBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationBindingModule::class,
    AndroidSupportInjectionModule::class,
    PresentationBindingModule::class,
    ThreadingBindingModule::class,
    ActivityBindingModule::class,
    DataModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: App)

}