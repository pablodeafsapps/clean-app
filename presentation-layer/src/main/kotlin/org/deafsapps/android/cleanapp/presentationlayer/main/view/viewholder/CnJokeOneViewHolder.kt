package org.deafsapps.android.cleanapp.presentationlayer.main.view.viewholder

import android.view.View
import android.widget.TextView
import es.sdos.component.recyclercomponent.BaseViewTypeHolder
import kotlinx.android.synthetic.main.joke_item_one.view.*
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter.CnJokeActionView
import org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter.CnJokeViewType

class CnJokeOneViewHolder(itemView: View) : BaseViewTypeHolder<CnJokeViewType, CnJokeActionView>(itemView) {

    private val container = itemView
    private val tvJoke: TextView? by lazy { itemView.joke_item_one__tv__joke }
    private val tvCategories: TextView? by lazy { itemView.joke_item_one__tv__categories }

    override fun onBind(item: CnJokeViewType, callback: (CnJokeActionView) -> Unit) {
        (item as? JokeVo)?.let { jokeVo ->
            tvJoke?.text = jokeVo.joke
            tvCategories?.text = jokeVo.categories.takeIf { it.isNotEmpty() }.toString()
            container.setOnClickListener {
                callback(CnJokeActionView.JokeItemTapped(item))
            }
        }
    }

}