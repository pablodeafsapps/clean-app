package org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter

import es.sdos.component.recyclercomponent.ActionView
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo

sealed class CnJokeActionView : ActionView() {

    data class JokeItemTapped(val item: JokeVo) : CnJokeActionView()

    object JokeItemLongClicked : CnJokeActionView()

}