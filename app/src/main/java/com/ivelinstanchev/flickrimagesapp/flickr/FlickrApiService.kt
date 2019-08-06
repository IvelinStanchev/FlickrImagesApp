package com.ivelinstanchev.flickrimagesapp.flickr

import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage

class FlickrApiService {

    fun fetchImages(page: Int,
                    searchQuery: String,
                    responseListener: GeneralResponseListener<List<FlickrImage>>) {
        val itemsPerPage = 20
        val startRange = page * 20
        val endRange = startRange + itemsPerPage - 1

        val images = ArrayList<FlickrImage>()
        for (i in startRange..endRange) {
            images.add(FlickrImage(i.toString(), "URL$i"))
        }

        responseListener.onSuccess(images)
    }
}