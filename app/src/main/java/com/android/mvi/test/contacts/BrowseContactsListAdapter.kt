package com.android.mvi.test.contacts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.mvi.test.R
import com.android.mvi.test.presentation.browse.model.ContactDisplayObject
import javax.inject.Inject

class BrowseContactsListAdapter @Inject constructor() : RecyclerView.Adapter<BrowseContactsListAdapter.ViewHolder>() {

    var contacts: List<ContactDisplayObject> = mutableListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameText.text = contact.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.li_contact, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = contacts.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameText: TextView = view.findViewById(R.id.text_name)
    }
}