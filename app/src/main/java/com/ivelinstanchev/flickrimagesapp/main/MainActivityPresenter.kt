package com.ivelinstanchev.flickrimagesapp.main

import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage


class MainActivityPresenter(
    private val view: MainActivityContract.View
) : MainActivityContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun fetchImages() {
        val images = ArrayList<FlickrImage>()
        images.add(FlickrImage("1", "URL 1"))
        images.add(FlickrImage("2", "URL 2"))
        images.add(FlickrImage("3", "URL 3"))
        images.add(FlickrImage("4", "URL 4"))
        images.add(FlickrImage("5", "URL 5"))

        view.initImagesRecycler(images)
    }
}