package es.plexus.android.plexuschuck.presentationlayer.feature.main.view.state

import es.plexus.android.plexuschuck.presentationlayer.base.BaseState
import es.plexus.android.plexuschuck.presentationlayer.domain.FailureVo
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo

sealed class MainState : BaseState() {
    object Idle : MainState()
    class ShowJokeList(val jokeList: List<JokeVo>) : MainState()
    class ShowError(val failure: FailureVo?) : MainState()
}