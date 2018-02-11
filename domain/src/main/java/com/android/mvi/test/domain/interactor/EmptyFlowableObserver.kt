package com.android.mvi.test.domain.interactor

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

open class EmptyFlowableObserver<T> : SingleObserver<T> {

    override fun onSubscribe(d: Disposable) { }

    override fun onSuccess(t: T) { }

    override fun onError(exception: Throwable) { }

}