package com.android.mvi.test.data

import com.android.mvi.test.domain.model.Contact
import java.util.Random
import java.util.concurrent.ThreadLocalRandom

class ContactDataFactory {

    companion object Factory {

        private val firstNames = arrayOf("Jayden", "Kuzman", "Alfher", "Kwesi", "Anaitis", "Leontius", "Iyov", "Rosa")
        private val secondName = arrayOf("Yevdokiya", "Lev", "Maksim", "Oksana", "Nata", "Anya", "Pasha", "Anastasia")

        private fun randomString(): String {
            val index = Random().nextInt(secondName.size)

            return firstNames[index] + " " + secondName[index]
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