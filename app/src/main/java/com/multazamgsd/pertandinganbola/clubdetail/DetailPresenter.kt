package com.multazamgsd.pertandinganbola.clubdetail

import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.api.TheSportDBApi
import com.multazamgsd.pertandinganbola.model.PlayerDetailResponse
import com.multazamgsd.pertandinganbola.model.PlayerResponse
import com.multazamgsd.pertandinganbola.model.TeamDetailResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson){

    fun loadMainData(team_id: String){
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.teamDetail(team_id)),
                        TeamDetailResponse::class.java
                )
            }
            view.showMainData(data.await().teams)
        }
    }

    fun loadPlayerList(team_id: String){
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerList(team_id)),
                        PlayerResponse::class.java
                )
            }
            view.showPlayerList(data.await().player)
        }
    }

}