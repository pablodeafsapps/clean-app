package org.deafsapps.android.cleanapp.presentationlayer.detail.view.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import org.deafsapps.android.cleanapp.presentationlayer.R
import org.deafsapps.android.cleanapp.presentationlayer.detail.DetailContract
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private val detailPresenter: DetailContract.Presenter? by inject { parametersOf(this) }
    private val pbLoading: ProgressBar? by lazy { activity_detail__pb__loading }
    private val tvId: TextView? by lazy { activity_detail__tv__id }
    private val tvJoke: TextView? by lazy { activity_detail__tv__joke }
    private val tvCategories: TextView? by lazy { activity_detail__tv__categories }
    private var jokeItem: JokeVo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        jokeItem = intent.getParcelableExtra("jokeItem") as? JokeVo
    }

    override fun onResume() {
        super.onResume()
        detailPresenter?.onViewResumed(jokeItem)
    }

    override fun showLoading() {
        pbLoading?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbLoading?.visibility = View.GONE
    }

    override fun showInfoMessage(msg: String) {
        toast(msg)
    }

    override fun loadJokeItem(item: JokeVo) {
        tvId?.text = item.id.toString()
        tvJoke?.text = item.joke
        tvCategories?.text = item.categories.toString()
    }

}