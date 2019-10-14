package com.multazamgsd.pertandinganbola.match

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.multazamgsd.pertandinganbola.R
import com.multazamgsd.pertandinganbola.model.Events

class PreviousAdapter(private val context: Context?, private val events: List<Events>, private val listener: (Events) -> Unit)
    : RecyclerView.Adapter<PreviousAdapter.PreviousViewHolder>() {
    private val TAG: String = "PreviousAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PreviousViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list, parent, false))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: PreviousViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    class PreviousViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val matchTitle = view.findViewById<TextView>(R.id.textViewMatch)
        private val matchDate = view.findViewById<TextView>(R.id.textViewMatchDate)

        fun bindItem(prev: Events, listener: (Events) -> Unit) {
            matchTitle.text = "${prev.strHomeTeam} ${prev.intHomeScore} vs. ${prev.intAwayScore} ${prev.strAwayTeam}"
            matchDate.text = prev.strDate
            itemView.setOnClickListener { listener(prev) }
        }
    }
}