package com.ivelinstanchev.flickrimagesapp.main.model

object FlickrImageLoading : FlickrAdapterItem {

    override fun getItemViewType() = FlickrAdapterItem.ITEM_LOADING

    override fun isSameAs(other: FlickrAdapterItem): Boolean {
        return other is FlickrImageLoading
    }

    override fun isContentEqualTo(other: FlickrAdapterItem): Boolean {
        return other is FlickrImageLoading
    }
}