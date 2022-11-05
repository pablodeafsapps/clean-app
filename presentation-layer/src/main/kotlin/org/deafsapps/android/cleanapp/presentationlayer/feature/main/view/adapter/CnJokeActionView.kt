package org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.adapter

import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo

/**
 * A sealed class which models all actions available for a joke view
 */
sealed class CnJokeActionView {
    data class JokeItemTapped(val item: JokeVo) : CnJokeActionView()
    object JokeItemLongSelected : CnJokeActionView()
}
