package com.android.mvi.test.injection.module

import com.android.mvi.test.contacts.BrowseContactListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): BrowseContactListActivity
}