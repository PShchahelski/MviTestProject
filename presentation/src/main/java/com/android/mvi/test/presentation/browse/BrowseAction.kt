package com.android.mvi.test.presentation.browse

import com.android.mvi.test.presentation.base.MviBaseAction

sealed class BrowseAction : MviBaseAction {

    object LoadAction : BrowseAction()

    object RefreshAction : BrowseAction()
}