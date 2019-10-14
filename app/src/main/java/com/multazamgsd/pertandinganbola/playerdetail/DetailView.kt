package com.multazamgsd.pertandinganbola.playerdetail

import com.multazamgsd.pertandinganbola.model.Player

interface DetailView{
    fun showMainData(data: List<Player>)
}