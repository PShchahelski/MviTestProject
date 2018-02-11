package com.android.mvi.test.data

import com.android.mvi.test.domain.model.Contact
import java.util.UUID.randomUUID
import java.util.concurrent.ThreadLocalRandom

class ContactDataFactory {

    companion object Factory {

        private fun randomString(): String {
            return randomUUID().toString()
        }

        private fun randomInt(): Int {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        }

        private fun randomLong(): Long {
            return randomInt().toLong()
        }

        private fun createContact(): Contact {
            return Contact(randomLong(), randomString())
        }

        fun createContactList(count: Int): List<Contact> {
            val contacts: MutableList<Contact> = mutableListOf()
            repeat(count) {
                contacts.add(createContact())
            }

            return contacts
        }
    }
}