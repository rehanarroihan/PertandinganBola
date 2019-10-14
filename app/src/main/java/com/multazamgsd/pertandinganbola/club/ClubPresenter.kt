package com.multazamgsd.pertandinganbola.club

import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.api.TheSportDBApi
import com.multazamgsd.pertandinganbola.model.TeamDetailResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class ClubPresenter(private val view: ClubView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    fun getTeamList(league: String?) {
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams(league)),
                        TeamDetailResponse::class.java)
            }
            view.loadClubList(data.await().teams)
        }
    }

    fun searchTeam(string: String?){
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamSearch(string)),
                        TeamDetailResponse::class.java)
            }
            view.loadClubList(data.await().teams)
        }
    }
}