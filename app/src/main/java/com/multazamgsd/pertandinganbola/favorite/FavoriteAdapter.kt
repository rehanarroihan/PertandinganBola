package com.multazamgsd.pertandinganbola.favorite

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.multazamgsd.pertandinganbola.R
import com.multazamgsd.pertandinganbola.db.Favorite

class FavoriteAdapter(private val context: Context?, private val fav: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.match_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(fav[position], listener)
    }

    override fun getItemCount(): Int = fav.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val matchTitle = view.findViewById<TextView>(R.id.textViewMatch)
        private val matchDate = view.findViewById<TextView>(R.id.textViewMatchDate)

        fun bindItem(fav: Favorite, listener: (Favorite) -> Unit) {
            if (fav.event_score == "null : null") {
                matchTitle.text = "${fav.team_home_name} vs ${fav.team_away_name}"
            } else {
                matchTitle.text = "${fav.team_home_name} ${fav.event_score} ${fav.team_away_name}"
            }
            if (fav.event_dte == null || fav.event_dte == "") {
                matchDate.text = "Soon"
            } else {
                matchDate.text = fav.event_dte
            }
            itemView.setOnClickListener { listener(fav) }
        }
    }
}