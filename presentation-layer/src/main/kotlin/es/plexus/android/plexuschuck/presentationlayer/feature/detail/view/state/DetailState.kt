package es.plexus.android.plexuschuck.presentationlayer.feature.detail.view.state

import es.plexus.android.plexuschuck.presentationlayer.base.BaseState
import es.plexus.android.plexuschuck.presentationlayer.domain.FailureVo
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo

sealed class DetailState : BaseState() {
    object Idle : DetailState()
    class ShowJokeInfo(val joke: JokeVo) : DetailState()
    class ShowError(val failure: FailureVo?) : DetailState()
}