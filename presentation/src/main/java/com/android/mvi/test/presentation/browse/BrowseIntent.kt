package com.android.mvi.test.presentation.browse

import com.android.mvi.test.presentation.base.MviBaseIntent

sealed class BrowseIntent : MviBaseIntent {

    object InitialIntent : BrowseIntent()

    object RefreshIntent : BrowseIntent()
}