package org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.viewholder

import android.view.View
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseViewHolder
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.adapter.CnJokeActionView
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.adapter.CnJokeView

/**
 * Holds the widgets composing the view which depicts joke data (type 2)
 *
 * It extends parametrized [BaseViewHolder] which constraints the data types handled
 */
class CnJokeTwoViewHolder(itemView: View) : BaseViewHolder<CnJokeView, CnJokeActionView>(itemView) {

    override fun onBind(item: CnJokeView, callback: (CnJokeActionView) -> Unit) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

}
