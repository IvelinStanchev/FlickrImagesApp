package com.ivelinstanchev.flickrimagesapp.listener

import android.graphics.Bitmap

interface ImageDownloadListener {

    fun onImageDownloaded(bitmap: Bitmap)

    fun onDownloadError()
}