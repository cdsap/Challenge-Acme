package com.cdsap.challenge.app.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cdsap.challenge.app.R
import com.cdsap.challenge.app.di.DomainModule
import com.cdsap.challenge.app.photo.ImageController
import com.cdsap.challenge.app.photo.PhotoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), CoroutineScope, SearchPhotosView {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private val job = SupervisorJob()

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: FrameLayout
    private lateinit var presenter: SearchPresenter
    private var refreshing = false
    private var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        bindView()
        initPresenter()
    }

    private fun initPresenter() {
        presenter = SearchPresenter(DomainModule.searchPhotos, this)
    }

    private fun bindView() {
        searchView = findViewById(R.id.search)
        setupSearchView()
        recyclerView = findViewById(R.id.recycler)
        progress = findViewById(R.id.progress)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val visibleThreshold = 3

                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if ((currentTotalCount <= lastItem + visibleThreshold) && !refreshing) {
                        launch {
                            refreshing = true
                            presenter.search(query, true)
                        }
                    }
                }
            }
        })

        val adapter = SearchAdapter(ImageController())
        recyclerView.adapter = adapter

    }

    private fun setupSearchView() {
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(value: String): Boolean {
                    if (query.isNotEmpty()) {
                        search(value)
                    }
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    return true
                }
            })
        }
    }

    private fun search(query: String) {
        hideKeyboard()
        this.query = query
        (recyclerView.adapter as SearchAdapter).setItems(mutableListOf(), false)
        searchView.clearFocus()
        progress.visibility = View.VISIBLE
        launch {
            presenter.search(query, false)
        }
    }

    private fun hideKeyboard() {
        val imm = searchView.context.getSystemService(Context
                .INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.windowToken, 0)
    }

    override fun showView(photos: List<PhotoViewModel>, refresh: Boolean) {
        refreshing = false
        progress.visibility = View.GONE
        (recyclerView.adapter as SearchAdapter).setItems(photos, refresh)
    }

    override fun showError(message: String) {
        progress.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
