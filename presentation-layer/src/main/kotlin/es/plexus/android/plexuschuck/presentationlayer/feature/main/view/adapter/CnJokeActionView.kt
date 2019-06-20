package es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter

import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo
import es.sdos.component.recyclercomponent.ActionView

sealed class CnJokeActionView : ActionView() {
    data class JokeItemTapped(val item: JokeVo) : CnJokeActionView()
    object JokeItemLongClicked : CnJokeActionView()
}