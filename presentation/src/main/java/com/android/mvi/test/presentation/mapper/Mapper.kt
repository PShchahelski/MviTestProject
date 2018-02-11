package com.android.mvi.test.presentation.mapper

interface Mapper<out V, in D> {

    fun mapToDisplayObject(item: D): V
}