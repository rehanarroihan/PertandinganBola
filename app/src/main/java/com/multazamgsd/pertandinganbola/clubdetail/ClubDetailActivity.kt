package com.multazamgsd.pertandinganbola.clubdetail

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.R
import com.multazamgsd.pertandinganbola.R.id.*
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.db.Team
import com.multazamgsd.pertandinganbola.db.database
import com.multazamgsd.pertandinganbola.model.Player
import com.multazamgsd.pertandinganbola.model.Teams
import com.multazamgsd.pertandinganbola.playerdetail.PlayerDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_club_detail.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ClubDetailActivity : AppCompatActivity(), DetailView {
    private var TAG: String = "ClubDetailActivity"
    private lateinit var snackbar: Snackbar
    private lateinit var teams: List<Teams>
    private lateinit var mAdapter: PlayerAdapter
    private lateinit var presenter: DetailPresenter
    private var mList: MutableList<Player> = mutableListOf()

    private var team_id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Club Detail"

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)

        mAdapter = PlayerAdapter(mList) {
            val intent = Intent(this, PlayerDetailActivity::class.java)
            intent.putExtra("player_id", it.idPlayer)
            startActivity(intent)
        }
        recyclerViewPlayer.layoutManager = LinearLayoutManager(this)
        recyclerViewPlayer.adapter = mAdapter
        recyclerViewPlayer.isNestedScrollingEnabled = false

        team_id = intent.extras.get("team_id").toString()
        Log.d(TAG, "team_id: $team_id")
        presenter.loadMainData(team_id)
        presenter.loadPlayerList(team_id)
    }

    override fun showMainData(data: List<Teams>) {
        teams = data
        Picasso.get().load(teams[0].strTeamBadge).into(ivTeamBadge)
        tvClubName.text = teams[0].strTeam
        tvTeamBornYear.text = teams[0].intFormedYear
        tvTeamStadium.text = teams[0].strStadium
        tvTeamDesc.text = teams[0].strDescriptionEN
    }

    override fun showPlayerList(data: List<Player>) {
        Log.d(TAG, "showPlayerList")
        Log.d(TAG, "data: $data")
        mList.clear()
        mList.addAll(data)
        Log.d(TAG, "mList: $mList")
        mAdapter.notifyDataSetChanged()
    }

    private fun isFavorite(): Boolean {
         var count = 0
        database.use {
            select("TABLE_TEAM").whereArgs("(TEAM_ID = ${team_id})")
                    .exec {
                        count = this.count
                    }
        }
        return count > 0
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Team.TABLE_TEAM,
                        Team.TEAM_ID to team_id,
                        Team.TEAM_NAME to teams[0].strTeam,
                        Team.TEAM_BADGE to teams[0].strTeamBadge)
            }
            snackbar = Snackbar.make(llClubDetail, "Added to Favorite", Snackbar.LENGTH_LONG)
            snackbar.show()
            invalidateOptionsMenu()
        } catch (e: SQLiteConstraintException) {
            snackbar = Snackbar.make(llClubDetail, e.localizedMessage, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    private fun deleteFromFavorite() {
        database.use {
            try {
                delete(Team.TABLE_TEAM, "${Team.TEAM_ID} = ${team_id}")
                snackbar = Snackbar.make(llClubDetail, "Deleted from Favorite", Snackbar.LENGTH_LONG)
                snackbar.show()
                invalidateOptionsMenu()
            } catch (e: SQLiteConstraintException) {
                snackbar = Snackbar.make(llClubDetail, e.localizedMessage, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            action_fav -> {
                addToFavorite()
                true
            }
            action_unfav -> {
                deleteFromFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onPrepareOptionsMenu")
        if (isFavorite()) {
            menu?.findItem(R.id.action_fav)?.isVisible = false
            menu?.findItem(R.id.action_unfav)?.isVisible = true
            Log.d(TAG, "onPrepareOptionsMenu: isFavorite")
        } else {
            menu?.findItem(R.id.action_fav)?.isVisible = true
            menu?.findItem(R.id.action_unfav)?.isVisible = false
            Log.d(TAG, "onPrepareOptionsMenu: not fav")
        }
        return super.onPrepareOptionsMenu(menu)
    }
}
