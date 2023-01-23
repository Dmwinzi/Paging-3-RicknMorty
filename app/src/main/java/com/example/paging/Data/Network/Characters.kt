package com.example.paging.Data.Network

data class Characters(

    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val url: String,
    val origin: String,
    val location: String,
    val episode: List<String>

)