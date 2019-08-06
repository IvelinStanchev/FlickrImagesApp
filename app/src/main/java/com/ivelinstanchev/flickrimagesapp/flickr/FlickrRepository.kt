package com.ivelinstanchev.flickrimagesapp.flickr

import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage

object FlickrRepository {

    private val apiService: FlickrApiService by lazy {
        FlickrApiService()
    }

    fun fetchImages(page: Int, responseListener: GeneralResponseListener<List<FlickrImage>>) {
        apiService.fetchImages(page, responseListener)
    }
}