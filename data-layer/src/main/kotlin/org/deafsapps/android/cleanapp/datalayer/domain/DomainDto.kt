package org.deafsapps.android.cleanapp.datalayer.domain

data class JokeDto(val id: Int, val joke: String, val categories: List<String>)

data class JokeDtoWrapper(val type: String, val value: List<JokeDto>)