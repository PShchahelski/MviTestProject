package com.android.mvi.test.presentation.browse

import android.arch.lifecycle.ViewModel
import com.android.mvi.test.presentation.base.MviBaseIntent
import com.android.mvi.test.presentation.base.MviBaseViewModel
import com.android.mvi.test.presentation.browse.BrowseResult.LoadTasksResult
import com.android.mvi.test.presentation.browse.BrowseResult.LoadTasksResult.Failure
import com.android.mvi.test.presentation.browse.BrowseResult.LoadTasksResult.InFlight
import com.android.mvi.test.presentation.browse.BrowseResult.LoadTasksResult.Success
import com.android.mvi.test.presentation.browse.BrowseResult.RefreshTasksResult
import com.android.mvi.test.presentation.browse.mapper.ContactMapper
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class BrowseContactListViewModel @Inject internal constructor(private val processor: BrowseListProcessor,
                                                              private val mapper: ContactMapper) : ViewModel(), MviBaseViewModel<BrowseIntent, BrowseUiViewState> {

    private var intentsSubject: PublishSubject<BrowseIntent> = PublishSubject.create()

    private val intentFilter: ObservableTransformer<BrowseIntent, BrowseIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                        shared.ofType(BrowseIntent.InitialIntent::class.java).take(1),
                        shared.filter { intent -> intent !is BrowseIntent.InitialIntent }
                )
            }
        }

    private val reducer: BiFunction<BrowseUiViewState, BrowseResult, BrowseUiViewState> =
            BiFunction { previousState, result ->
                when (result) {
                    is LoadTasksResult    -> {
                        when (result) {
                            is Success  -> previousState.copy(isLoading = false, contacts = result.contacts.map { mapper.mapToDisplayObject(it) })
                            is Failure  -> previousState.copy(isLoading = false, error = result.error)
                            is InFlight -> previousState.copy(isLoading = true)
                        }
                    }
                    is RefreshTasksResult -> {
                        when (result) {
                            is RefreshTasksResult.Success -> previousState.copy(isRefreshing = false, contacts = result.contacts.map {
                                mapper.mapToDisplayObject(it)
                            })
                            is RefreshTasksResult.Failure -> previousState.copy(isRefreshing = false, error = result.error)
                            RefreshTasksResult.InFlight   -> previousState.copy(isRefreshing = true)
                        }
                    }
                }
            }

    private val statesSubject: Observable<BrowseUiViewState> = compose()

    override fun processIntents(intents: Observable<BrowseIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<BrowseUiViewState> {
        return statesSubject
    }

    private fun compose(): Observable<BrowseUiViewState> {
        return intentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(processor.actionProcessor)
                .scan<BrowseUiViewState>(BrowseUiViewState.idle(), reducer)
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: MviBaseIntent): BrowseAction {
        return when (intent) {
            is BrowseIntent.RefreshIntent -> BrowseAction.RefreshAction
            is BrowseIntent.InitialIntent -> BrowseAction.LoadAction
            else                          -> throw UnsupportedOperationException("Oops, that looks like an unknown intent: " + intent)
        }
    }
}