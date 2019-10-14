package com.multazamgsd.pertandinganbola.model

import com.google.gson.annotations.SerializedName

data class Player(
        @SerializedName("idPlayer")
        var idPlayer: String,

        @SerializedName("idTeam")
        var teamId: String,

        @SerializedName("strCutout")
        var strCutout: String,

        @SerializedName("strPlayer")
        var strPlayer: String,

        @SerializedName("strPosition")
        var strPosition: String,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String,

        @SerializedName("strHeight")
        var strHeight: String,

        @SerializedName("strWeight")
        var strWeight: String
)