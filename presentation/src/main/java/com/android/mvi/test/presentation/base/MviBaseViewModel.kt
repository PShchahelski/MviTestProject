package com.android.mvi.test.presentation.base

import io.reactivex.Observable

interface MviBaseViewModel<I : MviBaseIntent, S : MviBaseViewState> {
    fun processIntents(intents: Observable<I>)

    fun states(): Observable<S>
}