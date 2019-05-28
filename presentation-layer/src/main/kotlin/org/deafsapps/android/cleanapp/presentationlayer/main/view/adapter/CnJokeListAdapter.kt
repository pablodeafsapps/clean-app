package org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter

import android.view.View
import es.sdos.component.recyclercomponent.BaseViewTypeAdapter
import es.sdos.component.recyclercomponent.BaseViewTypeHolder
import org.deafsapps.android.cleanapp.presentationlayer.R
import org.deafsapps.android.cleanapp.presentationlayer.main.view.viewholder.CnJokeOneViewHolder
import org.deafsapps.android.cleanapp.presentationlayer.main.view.viewholder.CnJokeTwoViewHolder

class CnJokeListAdapter(itemList: MutableList<CnJokeViewType>?, callback: (CnJokeActionView) -> Unit) :
    BaseViewTypeAdapter<CnJokeViewType, BaseViewTypeHolder<CnJokeViewType, CnJokeActionView>, CnJokeActionView>(
        itemList = itemList, callback = callback
    ) {

    override fun getHolder(viewType: Int, view: View): BaseViewTypeHolder<CnJokeViewType, CnJokeActionView> {
        return when (viewType) {
            CnJokeViewType.JokeViewType.TYPE_ONE.type -> CnJokeOneViewHolder(view)
            CnJokeViewType.JokeViewType.TYPE_TWO.type -> CnJokeTwoViewHolder(view)
            else -> CnJokeOneViewHolder(view)
        }
    }

    override fun getLayoutResourceIdByViewType(viewType: Int): Int {
        return when (viewType) {
            CnJokeViewType.JokeViewType.TYPE_ONE.type -> R.layout.joke_item_one
            CnJokeViewType.JokeViewType.TYPE_TWO.type -> R.layout.joke_item_two
            else -> -1
        }
    }

    fun updateData(items: List<CnJokeViewType>?) {
        items?.let {
            itemList?.addAll(it)
            notifyDataSetChanged()
        }
    }

}