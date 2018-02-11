package com.android.mvi.test.injection.module

import com.android.mvi.test.UiThread
import com.android.mvi.test.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module

@Module
abstract class ThreadingBindingModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread
}