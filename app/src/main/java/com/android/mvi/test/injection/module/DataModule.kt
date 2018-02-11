package com.android.mvi.test.injection.module

import com.android.mvi.test.data.ContactDataRepository
import com.android.mvi.test.data.executor.JobExecutor
import com.android.mvi.test.domain.executor.ThreadExecutor
import com.android.mvi.test.domain.repository.ContactRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindBufferooRepository(repository: ContactDataRepository): ContactRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}