package com.ivelinstanchev.flickrimagesapp.flickr

import com.ivelinstanchev.flickrimagesapp.error.ErrorResponse
import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImageApiResponse
import com.ivelinstanchev.flickrimagesapp.main.model.toDisplay

class FlickrRepository(private val apiService: FlickrApiService) {

    fun fetchImages(
        page: Int,
        searchQuery: String,
        responseListener: GeneralResponseListener<List<FlickrImage>>
    ) {
        apiService.fetchImages(
            page,
            searchQuery,
            object : GeneralResponseListener<List<FlickrImageApiResponse>> {
                override fun onSuccess(response: List<FlickrImageApiResponse>) {
                    val convertedList = response.map { it.toDisplay() }
                    responseListener.onSuccess(convertedList)
                }

                override fun onError(errorResponse: ErrorResponse) {
                    responseListener.onError(errorResponse)
                }
            })
    }
}