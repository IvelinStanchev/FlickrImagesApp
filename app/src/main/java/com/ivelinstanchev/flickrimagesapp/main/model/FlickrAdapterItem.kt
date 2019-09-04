package com.ivelinstanchev.flickrimagesapp.main.model

import android.support.v7.util.DiffUtil

/**
 * Used to mark Flickr images adapter items
 */
interface FlickrAdapterItem {

    companion object {
        const val ITEM_FLICKR_IMAGE = 1
        const val ITEM_LOADING = 2
    }

    fun getItemViewType(): Int

    fun isSameAs(other: FlickrAdapterItem): Boolean

    fun isContentEqualTo(other: FlickrAdapterItem): Boolean
}

object ImagesDiffUtil : DiffUtil.ItemCallback<FlickrAdapterItem>() {

    override fun areItemsTheSame(oldItem: FlickrAdapterItem, newItem: FlickrAdapterItem): Boolean {
        return oldItem.isSameAs(newItem)
    }

    override fun areContentsTheSame(
        oldItem: FlickrAdapterItem,
        newItem: FlickrAdapterItem
    ): Boolean {
        return oldItem.isContentEqualTo(newItem)
    }
}