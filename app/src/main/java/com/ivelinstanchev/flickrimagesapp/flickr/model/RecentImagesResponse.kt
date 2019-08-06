package com.ivelinstanchev.flickrimagesapp.flickr.model

import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImageApiResponse
import org.json.JSONObject

class RecentImagesResponse(rawJson: String) : JSONObject(rawJson) {
    val photos: List<FlickrImageApiResponse>? = this.optJSONObject("photos")?.optJSONArray("photo")
        ?.let { 0.until(it.length()).map { eachIndex -> it.optJSONObject(eachIndex) } }
        ?.map { FlickrImageApiResponse(it.toString()) }
}
