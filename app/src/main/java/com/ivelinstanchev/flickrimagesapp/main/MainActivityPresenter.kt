package com.ivelinstanchev.flickrimagesapp.main

import com.ivelinstanchev.flickrimagesapp.flickr.FlickrRepository
import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage

class MainActivityPresenter(
    private val view: MainActivityContract.View
) : MainActivityContract.Presenter {

    companion object {
        private const val INITIAL_PAGE = 0
    }

    private var page: Int = INITIAL_PAGE
    private var lastSearchQuery: String? = null
    private var imagesList: MutableList<FlickrImage> = mutableListOf()

    init {
        view.setPresenter(this)
    }

    override fun submitSearchQuery(searchQuery: String?) {
        if (searchQuery == null) {
            return
        }

        if (lastSearchQuery == searchQuery) {
            page++
        } else {
            lastSearchQuery = searchQuery
            resetData()
        }

        FlickrRepository.fetchImages(page, searchQuery, object : GeneralResponseListener<List<FlickrImage>> {
            override fun onSuccess(response: List<FlickrImage>) {
                imagesList.addAll(response)

                if (page == INITIAL_PAGE) {
                    view.initImagesRecycler(imagesList)
                } else {
                    view.updateImagesRecycler(imagesList)
                }
            }

            override fun onError(error: Throwable) {
                view.onImagesFetchError(error)
            }
        })
    }

    private fun resetData() {
        page = INITIAL_PAGE
        imagesList.clear()
    }
}