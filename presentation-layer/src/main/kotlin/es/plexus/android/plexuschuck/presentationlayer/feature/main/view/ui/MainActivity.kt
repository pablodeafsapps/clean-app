package es.plexus.android.plexuschuck.presentationlayer.feature.main.view.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import es.plexus.android.plexuschuck.domainlayer.feature.main.MainDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.R
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmView
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.FailureVo
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo
import es.plexus.android.plexuschuck.presentationlayer.feature.detail.view.ui.DetailActivity
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter.CnJokeActionView
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter.CnJokeListAdapter
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.state.MainState
import es.plexus.android.plexuschuck.presentationlayer.feature.main.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

const val INTENT_DATA_KEY = "jokeItem"

class MainActivity : AppCompatActivity(),
    BaseMvvmView<MainActivityViewModel, MainDomainLayerBridge<List<String>?, List<JokeBo>>, MainState> {

    override val viewModel: MainActivityViewModel? by viewModel()
    private val pbLoading: ProgressBar? by lazy { activity_main__pb__loading }
    private val rvJokes: RecyclerView? by lazy { activity_main__rv__todo_items }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initModel()
        initView()
    }

    override fun processRenderState(renderState: MainState?) {
        when (renderState) {
            is MainState.Idle -> hideLoading()
            is MainState.ShowJokeList -> loadJokesData(renderState.jokeList)
            is MainState.ShowJokeDetail -> navigateToDetailActivity(renderState.joke)
            is MainState.ShowError -> showError(renderState.failure)
        }
    }

    private fun initModel() {
        viewModel?.screenState?.observe(this, Observer { screenState ->
            when (screenState) {
                is ScreenState.Loading -> showLoading()
                is ScreenState.Render<MainState> -> processRenderState(screenState.renderState)
            }
        })
    }

    private fun initView() {
        rvJokes?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvJokes?.adapter = CnJokeListAdapter(itemList = mutableListOf()) { action ->
            when (action) {
                is CnJokeActionView.JokeItemTapped -> viewModel?.onJokeItemClicked(action.item)
                CnJokeActionView.JokeItemLongClicked -> longToast("Item long-clicked")
            }
        }
    }

    private fun loadJokesData(data: List<JokeVo>) {
        (rvJokes?.adapter as? CnJokeListAdapter)?.updateData(data)
    }

    private fun showLoading() {
        pbLoading?.visibility = View.VISIBLE
        rvJokes?.isEnabled = false
    }

    private fun hideLoading() {
        pbLoading?.visibility = View.GONE
        rvJokes?.isEnabled = true
    }

    private fun navigateToDetailActivity(item: JokeVo) {
        startActivity<DetailActivity>(INTENT_DATA_KEY to item)
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.getErrorMessage() != null) {
            toast(failure.getErrorMessage())
        }
    }

}