package com.android.mvi.test.presentation.base

import io.reactivex.Observable

interface MviBaseView<I : MviBaseIntent, in S : MviBaseViewState> {
    fun intents(): Observable<I>

    fun render(state: S)
}