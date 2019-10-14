package com.multazamgsd.pertandinganbola.match

import com.google.gson.Gson
import com.multazamgsd.pertandinganbola.TestContextProvider
import com.multazamgsd.pertandinganbola.api.ApiRepository
import com.multazamgsd.pertandinganbola.api.TheSportDBApi
import com.multazamgsd.pertandinganbola.model.Events
import com.multazamgsd.pertandinganbola.model.PreviousResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchFragmentTest {
    @Test
    fun getMatchList() {
        val teams: MutableList<Events> = mutableListOf()
        val response = PreviousResponse(teams)
        val league = "English Premiere League"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.eventsPastLeague(league)),
                PreviousResponse::class.java
        )).thenReturn(response)

        presenter.getPreviousMatchList(league)

        Mockito.verify(view).showPreviousMatchList(teams)
    }

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }
}