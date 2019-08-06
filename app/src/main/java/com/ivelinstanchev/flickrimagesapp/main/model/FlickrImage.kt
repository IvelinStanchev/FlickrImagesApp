package com.ivelinstanchev.flickrimagesapp.main.model

data class FlickrImage(
    val id: String,
    val url: String) : FlickrAdapterItem {

    override fun getItemViewType() = FlickrAdapterItem.ITEM_FLICKR_IMAGE

    override fun isSameAs(other: FlickrAdapterItem): Boolean {
        if (other is FlickrImage) {
            return id == other.id
        }

        return false
    }

    override fun isContentEqualTo(other: FlickrAdapterItem) = super.equals(other)
}