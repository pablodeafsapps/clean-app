package es.plexus.android.plexuschuck.presentationlayer.feature.detail.view.ui

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import es.plexus.android.plexuschuck.domainlayer.feature.detail.DetailDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.R
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmView
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.FailureVo
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo
import es.plexus.android.plexuschuck.presentationlayer.feature.detail.view.state.DetailState
import es.plexus.android.plexuschuck.presentationlayer.feature.detail.viewmodel.DetailActivityViewModel
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.ui.INTENT_DATA_KEY
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(),
    BaseMvvmView<DetailActivityViewModel, DetailDomainLayerBridge, DetailState> {

    override val viewModel: DetailActivityViewModel? by viewModel()
    private val pbLoading: ProgressBar? by lazy { activity_detail__pb__loading }
    private val tvId: TextView? by lazy { activity_detail__tv__id }
    private val tvJoke: TextView? by lazy { activity_detail__tv__joke }
    private val tvCategories: TextView? by lazy { activity_detail__tv__categories }
    private var jokeItem: JokeVo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initModel()

        jokeItem = intent.getParcelableExtra(INTENT_DATA_KEY) as? JokeVo
    }

    override fun onResume() {
        super.onResume()
        viewModel?.onViewCreated(jokeItem)
    }

    override fun processRenderState(renderState: DetailState?) {
        when (renderState) {
            is DetailState.Idle -> hideLoading()
            is DetailState.ShowJokeInfo -> loadJokeItem(renderState.joke)
            is DetailState.ShowError -> showError(renderState.failure)
        }
    }

    private fun initModel() {
        viewModel?.screenState?.observe(this, Observer { screenState ->
            when (screenState) {
                is ScreenState.Loading -> showLoading()
                is ScreenState.Render<DetailState> -> processRenderState(screenState.renderState)
            }
        })
    }

    private fun loadJokeItem(item: JokeVo) {
        tvId?.text = getString(R.string.tv_detail_id, item.id?.toString() ?: "")
        tvJoke?.text = Html.fromHtml(item.joke ?: "")
        tvCategories?.text = item.categories.takeIf { it?.isNotEmpty() == true }?.toString()
    }

    private fun showLoading() {
        pbLoading?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pbLoading?.visibility = View.GONE
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.getErrorMessage() != null) {
            toast(failure.getErrorMessage())
        }
    }

}