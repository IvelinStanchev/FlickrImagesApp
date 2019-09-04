package com.ivelinstanchev.flickrimagesapp.main

import com.ivelinstanchev.flickrimagesapp.error.ErrorResponse
import com.ivelinstanchev.flickrimagesapp.flickr.FlickrRepository
import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrAdapterItem
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImageLoading

class MainActivityPresenter(
    private val view: MainActivityContract.View,
    private val flickrRepository: FlickrRepository
) : MainActivityContract.Presenter {

    companion object {
        private const val INITIAL_PAGE = 1
    }

    private var page: Int = INITIAL_PAGE
    private var lastSearchQuery: String? = null
    private var imagesList: MutableList<FlickrAdapterItem> = mutableListOf()

    init {
        view.setPresenter(this)
    }

    override fun onRecyclerScrolledToBottom() {
        lastSearchQuery?.let {
            page++
            addLoadingItemAndSubmit()
            fetchData(page, it)
        }
    }

    override fun submitSearchQuery(searchQuery: String?) {
        if (searchQuery.isNullOrEmpty() || searchQuery == lastSearchQuery) {
            return
        }

        resetData()
        lastSearchQuery = searchQuery
        view.showMainLoading()
        fetchData(page, searchQuery)
    }

    private fun fetchData(page: Int, searchQuery: String) {
        flickrRepository.fetchImages(
            page,
            searchQuery,
            object : GeneralResponseListener<List<FlickrImage>> {
                override fun onSuccess(response: List<FlickrImage>) {

                    if (page == INITIAL_PAGE) {
                        view.hideMainLoading()
                    } else {
                        removeLoadingItem()
                    }

                    imagesList.addAll(response)

                    if (page == INITIAL_PAGE) {
                        view.initImagesRecycler(imagesList)
                    } else {
                        view.updateImagesRecycler(imagesList)
                    }
                }

                override fun onError(errorResponse: ErrorResponse) {
                    if (page == INITIAL_PAGE) {
                        view.hideMainLoading()
                    } else {
                        removeLoadingItemAndSubmit()
                    }

                    view.onImagesFetchError(errorResponse)
                }
            })
    }

    private fun addLoadingItemAndSubmit() {
        if (imagesList.isNotEmpty() &&
            imagesList.last() !is FlickrImageLoading
        ) {
            imagesList.add(FlickrImageLoading)

            view.updateImagesRecycler(imagesList)
        }
    }

    private fun removeLoadingItem(): Boolean {
        if (imagesList.isNotEmpty() &&
            imagesList.last() is FlickrImageLoading
        ) {
            imagesList.removeAt(imagesList.size - 1)
            return true
        }

        return false
    }

    private fun removeLoadingItemAndSubmit() {
        if (removeLoadingItem()) {
            view.updateImagesRecycler(imagesList)
        }
    }

    private fun resetData() {
        page = INITIAL_PAGE
        imagesList.clear()
    }
}