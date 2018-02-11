package com.android.mvi.test.presentation.browse

import com.android.mvi.test.presentation.base.MviBaseViewState
import com.android.mvi.test.presentation.browse.model.ContactDisplayObject

data class BrowseUiViewState(
        val isLoading: Boolean,
        val isRefreshing: Boolean,
        val contacts: List<ContactDisplayObject>?,
        val error: Throwable?) : MviBaseViewState {
    companion object {

        fun idle(): BrowseUiViewState {
            return BrowseUiViewState(
                    isLoading = false,
                    isRefreshing = false,
                    contacts = null,
                    error = null)
        }
    }
}
