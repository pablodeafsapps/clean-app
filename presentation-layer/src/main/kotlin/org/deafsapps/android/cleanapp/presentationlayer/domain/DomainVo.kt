package org.deafsapps.android.cleanapp.presentationlayer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter.CnJokeViewType

@Parcelize
data class JokeVo(val id: Int, val joke: String, val categories: List<String>) : CnJokeViewType.JokeTypeOne(),
    Parcelable