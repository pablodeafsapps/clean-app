package org.deafsapps.android.cleanapp.presentationlayer.feature.main.navigator

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseNavigator
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.deafsapps.android.cleanapp.presentationlayer.feature.detail.view.ui.DetailActivity
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.ui.INTENT_DATA_KEY
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.ui.MainActivity
import org.jetbrains.anko.startActivity

/**
 * This interface is the navigation from main feature
 */
interface MainNavigator<T> : BaseNavigator {

    /**
     * Navigate to detail activity
     * @param [item] data necessary to show detail
     */
    fun navigateToDetailActivity(item: T)

}

/**
 * This class implements the navigation from main feature
 */
@ExperimentalCoroutinesApi
class MainNavigatorImpl(private val activity: MainActivity) : MainNavigator<JokeVo> {

    override fun navigateToDetailActivity(item: JokeVo) {
        activity.startActivity<DetailActivity>(INTENT_DATA_KEY to item)
    }
}
