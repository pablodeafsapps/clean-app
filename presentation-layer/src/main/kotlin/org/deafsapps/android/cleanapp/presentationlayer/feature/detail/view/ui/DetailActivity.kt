package org.deafsapps.android.cleanapp.presentationlayer.feature.detail.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import org.deafsapps.android.cleanapp.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.R
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.databinding.ActivityDetailBinding
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.deafsapps.android.cleanapp.presentationlayer.feature.detail.view.state.DetailState
import org.deafsapps.android.cleanapp.presentationlayer.feature.detail.viewmodel.DetailViewModel
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.ui.INTENT_DATA_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.deafsapps.android.cleanapp.presentationlayer.feature.detail.navigator.DetailNavigator
import org.jetbrains.anko.toast
import org.koin.android.scope.ScopeActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * This [AppCompatActivity] displays the details of the selected [JokeVo]
 */
@ExperimentalCoroutinesApi
class DetailActivity : ScopeActivity(),
    BaseMvvmView<DetailViewModel, BaseDomainLayerBridge.None, DetailNavigator, DetailState> {

    override val viewModel: DetailViewModel by viewModel()
    private lateinit var viewBinding: ActivityDetailBinding
    private var jokeItem: JokeVo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDetailBinding.inflate(layoutInflater)
        initModel()
        setContentView(viewBinding.root)
        jokeItem = intent.getParcelableExtra(INTENT_DATA_KEY) as? JokeVo
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewCreated(jokeItem)
    }

    override fun processRenderState(renderState: DetailState) {
        when (renderState) {
            is DetailState.ShowJokeInfo -> loadJokeItem(item = renderState.joke)
            is DetailState.ShowError -> showError(failure = renderState.failure)
        }
    }

    override fun initModel() {
        lifecycleScope.launch {
            viewModel.screenState.collect { screenState ->
                when (screenState) {
                    is ScreenState.Idle -> hideLoading()
                    is ScreenState.Loading -> showLoading()
                    is ScreenState.Render<DetailState> -> processRenderState(screenState.renderState)
                }
            }
        }
    }

    private fun loadJokeItem(item: JokeVo) {
        with(viewBinding) {
            tvId.text = getString(R.string.tv_detail_id, item.id?.toString() ?: "")
            tvJoke.text = HtmlCompat.fromHtml(item.joke ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
            tvCategories.text = item.categories.takeIf { it?.isNotEmpty() == true }?.toString()
        }
    }

    private fun showLoading() {
        viewBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        viewBinding.pbLoading.visibility = View.GONE
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.getErrorMessage() != null) {
            toast(failure.getErrorMessage())
        }
    }

}
