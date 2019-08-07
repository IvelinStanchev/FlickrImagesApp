package com.ivelinstanchev.flickrimagesapp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.ivelinstanchev.flickrimagesapp.R
import com.ivelinstanchev.flickrimagesapp.di.Injector
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrAdapterItem
import com.ivelinstanchev.flickrimagesapp.scrolling.RecyclerEndlessScrollingManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    companion object {
        private const val SCROLL_THRESHOLD = 15
    }

    private lateinit var presenter: MainActivityContract.Presenter
    private lateinit var imagesAdapter: ImagesAdapter
    private val scrollManager = RecyclerEndlessScrollingManager(SCROLL_THRESHOLD) { presenter.onRecyclerScrolledToBottom() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = null
        setContentView(R.layout.activity_main)

        MainActivityPresenter(this, Injector.provideFlickrRepository())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView: SearchView? = menu?.findItem(R.id.menuItemMainSearch)?.actionView as? SearchView
        searchView?.maxWidth = Int.MAX_VALUE // making search view full width

        addSearchViewListener(searchView)

        return super.onCreateOptionsMenu(menu)
    }

    override fun setPresenter(presenter: MainActivityContract.Presenter) {
        this.presenter = presenter
    }

    override fun initImagesRecycler(images: List<FlickrAdapterItem>) {
        recyclerMainImages.layoutManager =
            GridLayoutManager(this, resources.getInteger(R.integer.main_recycler_images_grid_count))
        this.imagesAdapter = ImagesAdapter()
        recyclerMainImages.adapter = this.imagesAdapter
        this.imagesAdapter.submitList(images)
        recyclerMainImages.clearOnScrollListeners()
        recyclerMainImages.addOnScrollListener(scrollManager.getOnScrollListener())
    }

    override fun updateImagesRecycler(images: List<FlickrAdapterItem>) {
        this.imagesAdapter.submitList(images)
    }

    override fun onImagesFetchError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }

    override fun showMainLoading() {
        recyclerMainImages.visibility = View.GONE
        pbMain.visibility = View.VISIBLE
    }

    override fun hideMainLoading() {
        recyclerMainImages.visibility = View.VISIBLE
        pbMain.visibility = View.GONE
    }

    private fun addSearchViewListener(searchView: SearchView?) {
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                scrollManager.reset()
                presenter.submitSearchQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}
