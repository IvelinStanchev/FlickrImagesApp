package com.ivelinstanchev.flickrimagesapp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import com.ivelinstanchev.flickrimagesapp.R
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var presenter: MainActivityContract.Presenter
    private lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = null
        setContentView(R.layout.activity_main)

        MainActivityPresenter(this)
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

    override fun initImagesRecycler(images: List<FlickrImage>) {
        recyclerMainImages.layoutManager =
            GridLayoutManager(this, resources.getInteger(R.integer.main_recycler_images_grid_count))
        this.imagesAdapter = ImagesAdapter()
        recyclerMainImages.adapter = this.imagesAdapter
        this.imagesAdapter.submitList(images)
    }

    override fun updateImagesRecycler(images: List<FlickrImage>) {
        this.imagesAdapter.submitList(images)
    }

    override fun onImagesFetchError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }

    private fun addSearchViewListener(searchView: SearchView?) {
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.submitSearchQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}
