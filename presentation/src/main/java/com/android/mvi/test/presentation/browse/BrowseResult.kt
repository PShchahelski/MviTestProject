package com.android.mvi.test.presentation.browse

import com.android.mvi.test.domain.model.Contact
import com.android.mvi.test.presentation.base.MviBaseResult

sealed class BrowseResult : MviBaseResult {

    sealed class LoadTasksResult : BrowseResult() {
        data class Success(val contacts: List<Contact>) : LoadTasksResult()
        data class Failure(val error: Throwable) : LoadTasksResult()
        object InFlight : LoadTasksResult()
    }

    sealed class RefreshTasksResult : BrowseResult() {
        data class Success(val contacts: List<Contact>) : RefreshTasksResult()
        data class Failure(val error: Throwable) : RefreshTasksResult()
        object InFlight : RefreshTasksResult()
    }
}