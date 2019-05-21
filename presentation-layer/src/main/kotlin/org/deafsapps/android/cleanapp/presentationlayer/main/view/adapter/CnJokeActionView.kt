package org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter

import es.sdos.component.recyclercomponent.ActionView

sealed class CnJokeActionView : ActionView() {

    data class JokeItemTapped(val item: CnJokeViewType) : ActionView()

    object JokeItemLongClicked : ActionView()

}