package com.ivelinstanchev.flickrimagesapp.main

import com.ivelinstanchev.flickrimagesapp.flickr.FlickrRepository
import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage

class MainActivityPresenter(
    private val view: MainActivityContract.View
) : MainActivityContract.Presenter {

    private var page: Int = 0

    init {
        view.setPresenter(this)
    }

    override fun fetchImages() {

        FlickrRepository.fetchImages(page, object : GeneralResponseListener<List<FlickrImage>> {
            override fun onSuccess(response: List<FlickrImage>) {
                if (page == 0) {
                    view.initImagesRecycler(response)
                } else {
                    view.updateImagesRecycler(response)
                }
            }

            override fun onError(error: Throwable) {
                view.onImagesFetchError(error)
            }
        })
    }
}