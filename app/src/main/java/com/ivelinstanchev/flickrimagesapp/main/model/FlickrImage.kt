package com.ivelinstanchev.flickrimagesapp.main.model

import org.json.JSONObject

data class FlickrImage(
    val id: String,
    val title: String,
    val farm: Int,
    val server: String,
    val secret: String
) : FlickrAdapterItem {

    override fun getItemViewType() = FlickrAdapterItem.ITEM_FLICKR_IMAGE

    override fun isSameAs(other: FlickrAdapterItem): Boolean {
        if (other is FlickrImage) {
            return id == other.id
        }

        return false
    }

    override fun isContentEqualTo(other: FlickrAdapterItem) = super.equals(other)

    fun getUrl(): String {
        return "https://farm$farm.static.flickr.com/$server/${id}_$secret.jpg"
    }
}

class FlickrImageApiResponse(rawJson: String) : JSONObject(rawJson) {
    val id: String = this.optString("id")
    val title: String = this.optString("title")
    val farm: Int = this.optInt("farm")
    val server: String = this.optString("server")
    val secret: String = this.optString("secret")
}

fun FlickrImageApiResponse.toDisplay(): FlickrImage {
    return FlickrImage(
        id,
        title,
        farm,
        server,
        secret
    )
}