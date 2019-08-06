package com.ivelinstanchev.flickrimagesapp.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.widget.SearchView
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

        presenter.fetchImages()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView: SearchView? = menu?.findItem(R.id.menuItemMainSearch)?.actionView as? SearchView
        searchView?.maxWidth = Int.MAX_VALUE // making search view full width
        return super.onCreateOptionsMenu(menu)
    }

    override fun setPresenter(presenter: MainActivityContract.Presenter) {
        this.presenter = presenter
    }

    override fun initImagesRecycler(images: List<FlickrImage>) {
        recyclerMainImages.layoutManager =
            GridLayoutManager(this, resources.getInteger(R.integer.main_recycler_images_grid_count))
        this.imagesAdapter = ImagesAdapter(images)
        recyclerMainImages.adapter = this.imagesAdapter
    }

    override fun updateImagesRecycler(images: List<FlickrImage>) {
        this.imagesAdapter.submitList(images)
    }
}
