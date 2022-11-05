package org.deafsapps.android.cleanapp.presentationlayer.feature.detail.view.state

import org.deafsapps.android.cleanapp.presentationlayer.base.BaseState
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo

/**
 * Models all available states for the 'Detail' feature view
 */
sealed class DetailState : BaseState() {
    class ShowJokeInfo(val joke: JokeVo) : DetailState()
    class ShowError(val failure: FailureVo?) : DetailState()
}
