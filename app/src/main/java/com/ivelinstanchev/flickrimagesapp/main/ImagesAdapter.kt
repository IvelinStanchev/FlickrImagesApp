package com.ivelinstanchev.flickrimagesapp.main

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivelinstanchev.flickrimagesapp.R
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrAdapterItem
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage
import com.ivelinstanchev.flickrimagesapp.main.model.ImagesDiffUtil
import com.ivelinstanchev.flickrimagesapp.utils.ImageDownloader
import kotlinx.android.synthetic.main.item_main_image.view.*
import java.util.*
import kotlin.collections.ArrayList

class ImagesAdapter : ListAdapter<FlickrAdapterItem, RecyclerView.ViewHolder>(ImagesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FlickrAdapterItem.ITEM_FLICKR_IMAGE ->
                ImagesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_image, parent, false),
                    UUID.randomUUID().toString())
            else ->
                LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false))
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ImagesViewHolder) {
            viewHolder.bind(getItem(position) as FlickrImage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getItemViewType()
    }

    override fun submitList(list: List<FlickrAdapterItem>?) {
        super.submitList(ArrayList(list)) // should always submit new list because this is how diff util works
    }

    class ImagesViewHolder(view: View, private val uniqueHolderId: String) : RecyclerView.ViewHolder(view) {

        fun bind(image: FlickrImage) {
            itemView.imgMainImageItem.setImageBitmap(null) // refresh view holder image before loading new one
            ImageDownloader.loadWebImage(itemView.imgMainImageItem, image.getUrl(), uniqueHolderId)
        }
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}