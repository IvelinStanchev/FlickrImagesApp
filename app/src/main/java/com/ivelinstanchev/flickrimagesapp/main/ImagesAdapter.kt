package com.ivelinstanchev.flickrimagesapp.main

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivelinstanchev.flickrimagesapp.R
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage
import kotlinx.android.synthetic.main.item_main_image.view.*

class ImagesAdapter(private val images: List<FlickrImage>)
    : ListAdapter<FlickrImage, ImagesAdapter.ImagesViewHolder>(ImagesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_image, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(viewHolder: ImagesViewHolder, position: Int) {
        viewHolder.bind(images[position])
    }

    class ImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(image: FlickrImage) {
            itemView.itemMainImageTitle.text = image.url
        }
    }
}

private object ImagesDiffUtil : DiffUtil.ItemCallback<FlickrImage>() {

    override fun areItemsTheSame(oldItem: FlickrImage, newItem: FlickrImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FlickrImage, newItem: FlickrImage): Boolean {
        return oldItem == newItem
    }
}