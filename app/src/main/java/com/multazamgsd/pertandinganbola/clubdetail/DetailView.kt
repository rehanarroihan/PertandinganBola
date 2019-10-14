package com.multazamgsd.pertandinganbola.clubdetail

import com.multazamgsd.pertandinganbola.model.Player
import com.multazamgsd.pertandinganbola.model.Teams

interface DetailView{
    fun showMainData(data: List<Teams>)
    fun showPlayerList(data: List<Player>)
}