package com.ivelinstanchev.flickrimagesapp.main

import com.ivelinstanchev.flickrimagesapp.main.model.FlickrAdapterItem

interface MainActivityContract {

    interface View {

        fun setPresenter(presenter: Presenter)

        fun initImagesRecycler(images: List<FlickrAdapterItem>)

        fun updateImagesRecycler(images: List<FlickrAdapterItem>)

        fun onImagesFetchError(throwable: Throwable)

        fun showMainLoading()

        fun hideMainLoading()
    }

    interface Presenter {

        fun onRecyclerScrolledToBottom()

        fun submitSearchQuery(searchQuery: String?)
    }
}