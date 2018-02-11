package com.android.mvi.test.presentation.browse.mapper

import com.android.mvi.test.domain.model.Contact
import com.android.mvi.test.presentation.browse.model.ContactDisplayObject
import com.android.mvi.test.presentation.mapper.Mapper
import javax.inject.Inject

open class ContactMapper @Inject constructor() : Mapper<ContactDisplayObject, Contact> {

    override fun mapToDisplayObject(item: Contact) = with(item) {
        ContactDisplayObject(name)
    }
}