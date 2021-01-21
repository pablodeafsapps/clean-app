package org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.R
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.databinding.ActivityMainBinding
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.navigator.MainNavigator
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.adapter.CnJokeActionView
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.adapter.CnJokeListAdapter
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.state.MainState
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.koin.android.scope.ScopeActivity
import org.koin.android.viewmodel.ext.android.viewModel

const val INTENT_DATA_KEY = "jokeItem"

/**
 * This [AppCompatActivity] represents the main feature of the application. It is here where the
 * data of interest is rendered right after the ViewModel has handed them over.
 *
 * The UI state is controlled thanks to the collection of a [viewModel] observable variable.
 */
@ExperimentalCoroutinesApi
class MainActivity : ScopeActivity(),
    BaseMvvmView<MainViewModel, MainDomainLayerBridge<JokeBoWrapper>, MainNavigator<JokeVo>, MainState> {

    override val viewModel: MainViewModel by viewModel()
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        initModel()
        initView()
        setContentView(viewBinding.root)
        viewModel.onViewCreated()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.main_logout -> {
                viewModel.onLogoutSelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun processRenderState(renderState: MainState) {
        when (renderState) {
            is MainState.ShowJokeList -> loadJokesData(data = renderState.jokeList)
            is MainState.ShowJokeDetail -> viewModel.navigateToDetailActivity(item = renderState.joke)
            is MainState.ShowError -> showError(failure = renderState.failure)
            is MainState.QuitSession -> onBackPressed()
        }
    }

    override fun initModel() {
        lifecycleScope.launch {
            viewModel.screenState.collect { screenState ->
                when (screenState) {
                    is ScreenState.Idle -> hideLoading()
                    is ScreenState.Loading -> showLoading()
                    is ScreenState.Render<MainState> -> {
                        processRenderState(screenState.renderState)
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun initView() {
        with(viewBinding.rvItems) {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = CnJokeListAdapter(itemList = mutableListOf()) { action ->
                when (action) {
                    is CnJokeActionView.JokeItemTapped -> viewModel.onJokeItemSelected(action.item)
                    CnJokeActionView.JokeItemLongSelected -> longToast("Item long-clicked")
                }
            }
        }
    }

    private fun loadJokesData(data: List<JokeVo>) {
        with(viewBinding) {
            tvNoData.visibility = View.GONE
            (rvItems.adapter as? CnJokeListAdapter)?.updateData(data)
        }
    }

    private fun showLoading() {
        with(viewBinding) {
            pbLoading.visibility = View.VISIBLE
            rvItems.isEnabled = false
        }
    }

    private fun hideLoading() {
        with(viewBinding) {
            pbLoading.visibility = View.GONE
            rvItems.isEnabled = true
        }
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.getErrorMessage() != null) {
            viewBinding.tvNoData.visibility = View.VISIBLE
            toast(failure.getErrorMessage())
        }
    }

}
