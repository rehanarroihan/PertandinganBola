package com.multazamgsd.pertandinganbola.detail

import com.multazamgsd.pertandinganbola.model.Events

interface DetailView {
    fun showMainData(data: List<Events>)
}