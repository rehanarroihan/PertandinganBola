package com.multazamgsd.pertandinganbola

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.multazamgsd.pertandinganbola.R.id.*
import com.multazamgsd.pertandinganbola.R.layout.activity_main
import com.multazamgsd.pertandinganbola.club.ClubFragment
import com.multazamgsd.pertandinganbola.favorite.FavoriteFragment
import com.multazamgsd.pertandinganbola.match.MatchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                match -> {
                    match
                    loadPastMatchFragment(savedInstanceState)
                }

                club -> {
                    club
                    loadClubFragment(savedInstanceState)
                }

                favorites -> {
                    favorites
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = match
    }

    private fun loadPastMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchFragment(), MatchFragment::class.simpleName)
                    .commit()
        }
    }

    private fun loadClubFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, ClubFragment(), ClubFragment::class.simpleName)
                    .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteFragment(), FavoriteFragment::class.simpleName)
                    .commit()
        }
    }
}
