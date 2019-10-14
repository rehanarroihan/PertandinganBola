package com.multazamgsd.pertandinganbola.clubdetail

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.multazamgsd.pertandinganbola.R.id.*
import com.multazamgsd.pertandinganbola.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.listeners.onClick

class PlayerAdapter(private val player: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(player[position], listener)
    }

    override fun getItemCount(): Int = player.size
}

class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(8)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = team_badge_player
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        leftMargin = dip(8)
                    }
                    orientation = LinearLayout.VERTICAL

                    textView {
                        id = team_name_player
                        textSize = 18f
                    }.lparams {
                    }
                    textView {
                        id = team_position_player
                        textSize = 12f
                    }.lparams {
                        leftMargin = dip(4)
                        topMargin = dip(4)
                    }
                }
            }
        }
    }

}


class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val teamBadge: ImageView = view.find(team_badge_player)
    private val teamName: TextView = view.find(team_name_player)
    private val teamPosition: TextView = view.find(team_position_player)

    fun bindItem(player: Player, listener: (Player) -> Unit) {
        Picasso.get().load(player.strCutout).into(teamBadge)
        teamName.text = player.strPlayer
        teamPosition.text = player.strPosition
        itemView.onClick { listener(player) }
    }
}