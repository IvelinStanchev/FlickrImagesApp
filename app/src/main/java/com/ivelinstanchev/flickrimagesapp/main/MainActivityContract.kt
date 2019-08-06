package com.ivelinstanchev.flickrimagesapp.main

import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage

interface MainActivityContract {

    interface View {

        fun setPresenter(presenter: Presenter)

        fun initImagesRecycler(images: List<FlickrImage>)

        fun updateImagesRecycler(images: List<FlickrImage>)

        fun onImagesFetchError(throwable: Throwable)
    }

    interface Presenter {

        fun submitSearchQuery(searchQuery: String?)
    }
}