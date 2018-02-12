package com.android.mvi.test.contacts

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.android.mvi.test.R
import com.android.mvi.test.presentation.base.MviBaseView
import com.android.mvi.test.presentation.browse.BrowseContactListViewModel
import com.android.mvi.test.presentation.browse.BrowseIntent
import com.android.mvi.test.presentation.browse.BrowseUiViewState
import com.android.mvi.test.presentation.browse.model.ContactDisplayObject
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.a_main.list
import kotlinx.android.synthetic.main.a_main.progress
import kotlinx.android.synthetic.main.a_main.refresh_layout
import javax.inject.Inject

class BrowseContactListActivity : AppCompatActivity(), MviBaseView<BrowseIntent, BrowseUiViewState> {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var browseAdapter: BrowseContactsListAdapter

    private val compositeDisposable = CompositeDisposable()

    private lateinit var viewModel: BrowseContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)

        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowseContactListViewModel::class.java)

        initList()
        refresh_layout.setScrollUpChild(list)

        compositeDisposable.add(viewModel.states().subscribe(this::render))
        viewModel.processIntents(intents())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun intents(): Observable<BrowseIntent> {
        return Observable.merge(initialIntent(),
                refreshIntent())
    }

    override fun render(state: BrowseUiViewState) {
        refresh_layout.isRefreshing = state.isRefreshing

        if (state.isLoading) {
            setupScreenForLoadingState()
        }

        if (state.contacts != null) {
            setupScreenForSuccess(state.contacts)
        }
    }

    private fun initList() {
        list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = browseAdapter
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<ContactDisplayObject>?) {
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            list.visibility = View.VISIBLE
        }
    }

    private fun updateListView(contacts: List<ContactDisplayObject>) {
        browseAdapter.contacts = contacts
        browseAdapter.notifyDataSetChanged()
    }

    private fun initialIntent(): Observable<BrowseIntent.InitialIntent> {
        return Observable.just(BrowseIntent.InitialIntent)
    }

    private fun refreshIntent(): Observable<BrowseIntent.RefreshIntent> {
        return RxSwipeRefreshLayout.refreshes(refresh_layout)
                .map { BrowseIntent.RefreshIntent }
    }
}
