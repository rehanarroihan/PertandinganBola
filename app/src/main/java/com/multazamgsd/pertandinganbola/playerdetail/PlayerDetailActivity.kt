package com.multazamgsd.pertandinganbola.playerdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.R
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity(), DetailView {
    private lateinit var player: List<Player>
    private lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Player Detail"

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        presenter.loadMainData(intent.extras.get("player_id").toString())
    }

    override fun showMainData(data: List<Player>) {
        player = data
        Picasso.get().load(player[0].strCutout).into(imageViewPlayer)
        textViewPlayerName.text = player[0].strPlayer
        textViewPlayerDesc.text = player[0].strDescriptionEN
        textViewHeight.text = "Height \n ${player[0].strHeight}"
        textViewWeight.text = "Weight \n ${player[0].strWeight}"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
