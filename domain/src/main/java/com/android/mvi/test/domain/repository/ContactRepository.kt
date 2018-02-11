package com.android.mvi.test.domain.repository

import com.android.mvi.test.domain.model.Contact
import io.reactivex.Flowable

interface ContactRepository {

    fun getContacts(): Flowable<List<Contact>>
}