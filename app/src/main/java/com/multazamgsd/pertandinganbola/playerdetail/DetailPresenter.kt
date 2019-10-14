package com.multazamgsd.pertandinganbola.playerdetail

import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.api.TheSportDBApi
import com.multazamgsd.pertandinganbola.model.PlayerDetailResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson) {

    fun loadMainData(player_id: String) {
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerDetail(player_id)),
                        PlayerDetailResponse::class.java
                )
            }
            view.showMainData(data.await().players)
        }
    }
}