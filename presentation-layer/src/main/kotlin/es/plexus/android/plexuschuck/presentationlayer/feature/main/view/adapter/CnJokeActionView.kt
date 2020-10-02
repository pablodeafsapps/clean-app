package es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter

import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo

sealed class CnJokeActionView {
    data class JokeItemTapped(val item: JokeVo) : CnJokeActionView()
    object JokeItemLongSelected : CnJokeActionView()
}