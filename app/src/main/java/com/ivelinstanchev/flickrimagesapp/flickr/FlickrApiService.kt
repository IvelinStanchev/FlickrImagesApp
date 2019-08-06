package com.ivelinstanchev.flickrimagesapp.flickr

import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage

class FlickrApiService {

    fun fetchImages(page: Int, responseListener: GeneralResponseListener<List<FlickrImage>>) {
        val images = ArrayList<FlickrImage>()
        images.add(FlickrImage("1", "URL 1"))
        images.add(FlickrImage("2", "URL 2"))
        images.add(FlickrImage("3", "URL 3"))
        images.add(FlickrImage("4", "URL 4"))
        images.add(FlickrImage("5", "URL 5"))

        responseListener.onSuccess(images)
    }
}