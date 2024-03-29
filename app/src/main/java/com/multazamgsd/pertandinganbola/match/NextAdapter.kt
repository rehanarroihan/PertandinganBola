package com.multazamgsd.pertandinganbola.match

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.multazamgsd.pertandinganbola.R
import com.multazamgsd.pertandinganbola.R.id.textViewMatch
import com.multazamgsd.pertandinganbola.R.id.textViewMatchDate
import com.multazamgsd.pertandinganbola.model.Events

class NextAdapter(private val context: Context?, private val prev: List<Events>, private val listener: (Events) -> Unit)
    : RecyclerView.Adapter<NextAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(prev[position], listener)
    }

    override fun getItemCount(): Int = prev.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val matchTitle = view.findViewById<TextView>(textViewMatch)
        private val matchDate = view.findViewById<TextView>(textViewMatchDate)

        fun bindItem(prev: Events, listener: (Events) -> Unit) {
            matchTitle.text = prev.strEvent
            if (prev.strDate == null || prev.strDate == "") {
                matchDate.text = "Soon"
            } else {
                matchDate.text = prev.strDate
            }
            itemView.setOnClickListener { listener(prev) }
        }
    }
}