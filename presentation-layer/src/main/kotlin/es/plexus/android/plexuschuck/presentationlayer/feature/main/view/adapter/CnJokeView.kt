package es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter

sealed class CnJokeView(val viewType: Int)  {

    enum class JokeViewType(val type: Int) {
        TYPE_ONE(type = 1), TYPE_TWO(type = 2)
    }

    open class JokeTypeOne : CnJokeView(viewType = JokeViewType.TYPE_ONE.type)
    open class JokeTypeTwo : CnJokeView(viewType = JokeViewType.TYPE_TWO.type)

}