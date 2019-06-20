package es.plexus.android.plexuschuck.presentationlayer.feature.main.view.viewholder

import android.text.Html
import android.view.View
import android.widget.TextView
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter.CnJokeActionView
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter.CnJokeViewType
import es.sdos.component.recyclercomponent.BaseViewTypeHolder
import kotlinx.android.synthetic.main.joke_item_one.view.*

class CnJokeOneViewHolder(itemView: View) : BaseViewTypeHolder<CnJokeViewType, CnJokeActionView>(itemView) {

    private val container = itemView
    private val tvJoke: TextView? by lazy { itemView.joke_item_one__tv__joke }
    private val tvCategories: TextView? by lazy { itemView.joke_item_one__tv__categories }

    override fun onBind(item: CnJokeViewType, callback: (CnJokeActionView) -> Unit) {
        (item as? JokeVo)?.let { jokeVo ->
            tvJoke?.text = Html.fromHtml(jokeVo.joke)
            tvCategories?.text = jokeVo.categories.takeIf { it.isNotEmpty() }?.toString()
            container.setOnClickListener {
                callback(CnJokeActionView.JokeItemTapped(item))
            }
        }
    }

}