package com.multazamgsd.pertandinganbola.club

import com.multazamgsd.pertandinganbola.model.Teams

interface ClubView {
    fun loadClubList(data: List<Teams>)
}