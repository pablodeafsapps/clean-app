package org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.state

import org.deafsapps.android.cleanapp.presentationlayer.base.BaseState
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo

/**
 * Models all available states for the 'Main' feature view
 */
sealed class MainState : BaseState() {
    class ShowJokeList(val jokeList: List<JokeVo>) : MainState()
    class ShowJokeDetail(val joke: JokeVo) : MainState()
    class ShowError(val failure: FailureVo?) : MainState()
    object QuitSession : MainState()
}
