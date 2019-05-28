package org.deafsapps.android.cleanapp.presentationlayer.main.view.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.deafsapps.android.cleanapp.presentationlayer.DetailActivity
import org.deafsapps.android.cleanapp.presentationlayer.R
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.deafsapps.android.cleanapp.presentationlayer.main.MainContract
import org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter.CnJokeActionView
import org.deafsapps.android.cleanapp.presentationlayer.main.view.adapter.CnJokeListAdapter
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainContract.View {

    private val mainPresenter: MainContract.Presenter? by inject { parametersOf(this) }
    private val pbLoading: ProgressBar? by lazy { activity_main__pb__loading }
    private val rvJokes: RecyclerView? by lazy { activity_main__rv__todo_items }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvJokes?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvJokes?.adapter = CnJokeListAdapter(itemList = mutableListOf()) { action ->
            when (action) {
                is CnJokeActionView.JokeItemTapped -> mainPresenter?.onJokeItemClicked(action.item)
                CnJokeActionView.JokeItemLongClicked -> longToast("Item long-clicked")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainPresenter?.onViewResumed()
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

    override fun loadJokesData(data: List<JokeVo>) {
        (rvJokes?.adapter as? CnJokeListAdapter)?.updateData(data)
    }

    override fun navigateToDetailActivity(item: JokeVo) {
        startActivity<DetailActivity>()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter?.onDetach()
    }

}