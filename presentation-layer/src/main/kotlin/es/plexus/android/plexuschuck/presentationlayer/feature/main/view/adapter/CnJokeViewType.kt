package es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter

import es.sdos.component.recyclercomponent.BaseViewTypeItem

sealed class CnJokeViewType(viewType: Int) : BaseViewTypeItem(viewType) {

    enum class JokeViewType(val type: Int) {
        TYPE_ONE(type = 1), TYPE_TWO(type = 2)
    }

    open class JokeTypeOne : CnJokeViewType(JokeViewType.TYPE_ONE.type)

    open class JokeTypeTwo : CnJokeViewType(JokeViewType.TYPE_TWO.type)

}