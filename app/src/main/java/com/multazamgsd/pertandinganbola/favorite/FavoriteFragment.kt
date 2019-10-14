package com.multazamgsd.pertandinganbola.favorite

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.multazamgsd.pertandinganbola.R
import com.multazamgsd.pertandinganbola.R.color.colorAccent
import com.multazamgsd.pertandinganbola.clubdetail.ClubDetailActivity
import com.multazamgsd.pertandinganbola.db.Favorite
import com.multazamgsd.pertandinganbola.db.Team
import com.multazamgsd.pertandinganbola.db.database
import com.multazamgsd.pertandinganbola.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteFragment : Fragment() {
    private var TAG = "FavoriteFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeFavorite.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        swipeFavorite.onRefresh {
            showFavoriteList()
            showTeamList()
        }
        showFavoriteList()
        showTeamList()
    }

    //match
    private fun showFavoriteList() {
        swipeFavorite.isRefreshing = true
        activity?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val mList = result.parseList(classParser<Favorite>())
            if (mList.isNotEmpty()) {
                tvFavoriteMatchNull.visibility = View.GONE
                recyclerViewFavMatch.layoutManager = LinearLayoutManager(activity)
                recyclerViewFavMatch.visibility = View.VISIBLE
                recyclerViewFavMatch.adapter = FavoriteAdapter(activity, mList) {
                    val i = Intent(activity, DetailActivity::class.java)
                    i.putExtra("event_id", it.event_id)
                    startActivity(i)
                }
            } else {
                recyclerViewFavMatch.visibility = View.GONE
                tvFavoriteMatchNull.visibility = View.VISIBLE
                Log.d(TAG, "mList is empty")
            }
        }
        swipeFavorite.isRefreshing = false
    }

    //team
    private fun showTeamList() {
        swipeFavorite.isRefreshing = true
        activity?.database?.use {
            val result = select(Team.TABLE_TEAM)
            val mLists = result.parseList(classParser<Team>())
            if (mLists.isNotEmpty()) {
                tvFavoriteClubNull.visibility = View.GONE
                recyclerViewFavClub.layoutManager = LinearLayoutManager(activity)
                recyclerViewFavClub.visibility = View.VISIBLE
                recyclerViewFavClub.adapter = ClubAdapter(mLists) {
                    val i = Intent(activity, ClubDetailActivity::class.java)
                    i.putExtra("team_id", it.id_team)
                    startActivity(i)
                }
            } else {
                recyclerViewFavClub.visibility = View.GONE
                tvFavoriteClubNull.visibility = View.VISIBLE
                Log.d(TAG, "mLists is empty")
            }
        }
        swipeFavorite.isRefreshing = false
    }
}
