package fr.webflux.myspotify.domain

data class Artist(
    val id: String,
    val name: String,
    val followerNumbers: Int,
    val popularity: Int,
)
