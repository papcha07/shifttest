package com.example.testovoe.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testovoe.R
import com.example.testovoe.domain.models.PersonPreview

class PersonAdapter(
    private val context: Context,
    private val personList: MutableList<PersonPreview>,
    private val onPersonClick: (PersonPreview) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.person_item_view, parent, false)
        return PersonViewHolder(view, onPersonClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PersonViewHolder && position < personList.size) {
            val vacancy = personList[position]
            holder.bind(vacancy)
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class PersonViewHolder(
        itemView: View,
        onPersonClick: (PersonPreview) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.user_photo)
        private val address: TextView = itemView.findViewById(R.id.user_address)
        private val phone: TextView = itemView.findViewById(R.id.user_phone)
        private val userName: TextView = itemView.findViewById(R.id.user_name)

        private lateinit var person: PersonPreview

        init {
            itemView.setOnClickListener {
                onPersonClick(person)
            }
        }

        fun bind(person: PersonPreview) {
            this.person = person

            Glide.with(itemView.context)
                .load(person.thumbnail)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)

            address.text = "${person.streetName} ${person.streetNumber}"
            phone.text = person.phone
            userName.text = "${person.first} ${person.last}"
        }
    }

    fun setList(newPersons: List<PersonPreview>) {
        personList.clear()
        personList.addAll(newPersons)
        notifyDataSetChanged()
    }

}