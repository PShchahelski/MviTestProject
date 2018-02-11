package com.android.mvi.test.widget

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View

class ScrollChildSwipeRefreshLayout : SwipeRefreshLayout {

    private var scrollUpChild: View? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun canChildScrollUp(): Boolean {
        return scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()
    }

    fun setScrollUpChild(view: View) {
        scrollUpChild = view
    }
}
