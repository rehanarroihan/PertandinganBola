package com.multazamgsd.pertandinganbola.model

import com.google.gson.annotations.SerializedName

data class Teams(
        @SerializedName("idTeam")
        var idTeam: String,

        @SerializedName("strTeam")
        var strTeam: String,

        @SerializedName("strTeamShort")
        var strTeamShort: String,

        @SerializedName("strAlternate")
        var strAlternate: String,

        @SerializedName("strTeamBadge")
        var strTeamBadge: String,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String,

        @SerializedName("strStadium")
        var strStadium: String,

        @SerializedName("intFormedYear")
        var intFormedYear: String
)