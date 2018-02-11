package com.android.mvi.test.presentation.browse

import com.android.mvi.test.domain.interactor.browse.GetContactList
import com.android.mvi.test.presentation.browse.BrowseAction.LoadAction
import com.android.mvi.test.presentation.browse.BrowseAction.RefreshAction
import com.android.mvi.test.presentation.browse.BrowseResult.LoadTasksResult
import com.android.mvi.test.presentation.browse.BrowseResult.RefreshTasksResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class BrowseListProcessor @Inject constructor(private val getContactList: GetContactList) {

    private val loadProcessor: ObservableTransformer<LoadAction, BrowseResult>
            = ObservableTransformer { actions ->
        actions.flatMap {
            getContactList.execute()
                    .toObservable()
                    .map { list -> LoadTasksResult.Success(list) }
                    .cast(LoadTasksResult::class.java)
                    .onErrorReturn(LoadTasksResult::Failure)
                    .startWith(LoadTasksResult.InFlight)
        }
    }

    private val refreshProcessor: ObservableTransformer<RefreshAction, BrowseResult>
            = ObservableTransformer { actions ->
        actions.flatMap {
            getContactList.execute()
                    .toObservable()
                    .map { list -> RefreshTasksResult.Success(list) }
                    .cast(RefreshTasksResult::class.java)
                    .onErrorReturn(RefreshTasksResult::Failure)
                    .startWith(RefreshTasksResult.InFlight)
        }
    }

    internal var actionProcessor: ObservableTransformer<BrowseAction, BrowseResult> = ObservableTransformer { actions ->
        actions.publish { shared ->
            Observable.merge(
                    shared.ofType(LoadAction::class.java)
                            .compose(loadProcessor),
                    shared.ofType(RefreshAction::class.java)
                            .compose(refreshProcessor))
                    .mergeWith(shared.filter { it !is LoadAction && it !is RefreshAction }
                            .flatMap {
                                Observable.error<BrowseResult>(
                                        IllegalArgumentException("Unknown Action type"))
                            })
        }
    }
}