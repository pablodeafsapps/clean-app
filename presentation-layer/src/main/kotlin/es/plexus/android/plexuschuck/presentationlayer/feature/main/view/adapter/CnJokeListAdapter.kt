package es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.plexus.android.plexuschuck.presentationlayer.R
import es.plexus.android.plexuschuck.presentationlayer.base.BaseViewHolder
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.viewholder.CnJokeOneViewHolder

/**
 *
 */
class CnJokeListAdapter(
    private var itemList: List<CnJokeView>,
    private val onItemSelected: (CnJokeActionView) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<CnJokeView, CnJokeActionView>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CnJokeView, CnJokeActionView> {
        val layoutRes = getLayoutResourceIdByViewType(viewType = viewType)
        return CnJokeOneViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int = itemList[position].viewType

    override fun onBindViewHolder(
        holder: BaseViewHolder<CnJokeView, CnJokeActionView>,
        position: Int
    ) {
        holder.onBind(
            item = itemList[position],
            callback = { item -> onItemSelected.invoke(item) }
        )
    }

    /**
     *
     */
    fun updateData(newData: List<CnJokeView>) {
        itemList = newData
        notifyDataSetChanged()
    }

    private fun getLayoutResourceIdByViewType(viewType: Int): Int = when (viewType) {
        CnJokeView.JokeViewType.TYPE_ONE.type -> R.layout.joke_item_one
        CnJokeView.JokeViewType.TYPE_TWO.type -> R.layout.joke_item_two
        else -> -1
    }

}
