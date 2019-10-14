package com.multazamgsd.pertandinganbola.db

data class Team(val id: Long?, val id_team: String?, val team_name: String?,
                    val team_badge: String?) {

    companion object {
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}