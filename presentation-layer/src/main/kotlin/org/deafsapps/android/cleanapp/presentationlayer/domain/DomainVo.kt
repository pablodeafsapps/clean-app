package org.deafsapps.android.cleanapp.presentationlayer.domain

import org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter.CnJokeViewType

data class JokeVo(val id: Int, val joke: String, val categories: List<String>) : CnJokeViewType.JokeTypeOne()