package com.multazamgsd.pertandinganbola.club

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.multazamgsd.pertandinganbola.R.id.team_badge
import com.multazamgsd.pertandinganbola.R.id.team_name
import com.multazamgsd.pertandinganbola.model.Teams
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.listeners.onClick

class ClubAdapter(private val teams: List<Teams>, private val listener: (Teams) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size
}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = team_badge
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = team_name
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }

            }
        }
    }

}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindItem(team: Teams, listener: (Teams) -> Unit) {
        Picasso.get().load(team.strTeamBadge).into(teamBadge)
        teamName.text = team.strTeam
        itemView.onClick { listener(team) }
    }
}