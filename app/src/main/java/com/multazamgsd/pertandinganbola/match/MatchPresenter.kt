package com.multazamgsd.pertandinganbola.match

import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.CoroutineContextProvider
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.api.TheSportDBApi
import com.multazamgsd.pertandinganbola.model.MatchSearchResponse
import com.multazamgsd.pertandinganbola.model.NextResponse
import com.multazamgsd.pertandinganbola.model.PreviousResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPreviousMatchList(league: String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.eventsPastLeague(league)),
                        PreviousResponse::class.java)
            }
            view.showPreviousMatchList(data.await().events)
        }
    }

    fun getNextMatchList(league: String?) {
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.eventsNextMatch(league)),
                        NextResponse::class.java
                )
            }
            view.showNextMatchList(data.await().events)
        }
    }

    fun getMatchSearch(query: String?){
        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventSearch(query)),
                        MatchSearchResponse::class.java
                )
            }
            view.showNextMatchList(data.await().event)
        }
    }
}