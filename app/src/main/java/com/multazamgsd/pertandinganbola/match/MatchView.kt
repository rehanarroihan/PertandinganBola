package com.multazamgsd.pertandinganbola.match

import com.multazamgsd.pertandinganbola.model.Events

interface MatchView {
    fun showPreviousMatchList(data: List<Events>)
    fun showNextMatchList(data: List<Events>)
    fun showSearchMatchList(data: List<Events>)
}