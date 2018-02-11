package com.android.mvi.test.data

import com.android.mvi.test.data.ContactDataFactory.Factory.createContactList
import com.android.mvi.test.domain.model.Contact
import com.android.mvi.test.domain.repository.ContactRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ContactDataRepository @Inject constructor() : ContactRepository {

    override fun getContacts(): Flowable<List<Contact>> {
        return Flowable.fromCallable {
            Thread.sleep(5000L)

            createContactList(50)
        }
    }
}