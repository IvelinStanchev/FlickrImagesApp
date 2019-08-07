package com.ivelinstanchev.flickrimagesapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.ivelinstanchev.flickrimagesapp.R
import com.ivelinstanchev.flickrimagesapp.cache.ImageCache
import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import java.net.URL

/**
 * Responsible for loading remote image into ImageView and downloading only latest requested URL
 */
object ImageDownloader {

    private val downloadTasks: HashMap<String, AsyncTask<*, *, *>> = hashMapOf()

    fun loadWebImage(imageView: ImageView,
                     url: String,
                     uniqueId: String,
                     @DrawableRes errorImagePlaceholder: Int = R.drawable.ic_image_load_error_placeholder) {
        if (downloadTasks.containsKey(uniqueId)) {
            // Task was already executed for this id -> cancel it
            downloadTasks[uniqueId]?.cancel(false)
        }

        val bitmapCache = ImageCache.getBitmap(url)
        if (bitmapCache != null) {
            // Load image from cache
            imageView.setImageBitmap(bitmapCache)
            return
        }

        downloadTasks[uniqueId] = DownloadImageTask(object : GeneralResponseListener<Bitmap> {
            override fun onSuccess(response: Bitmap) {
                imageView.setImageBitmap(response)
                ImageCache.putBitmap(url, response)
            }

            override fun onError(error: Throwable) {
                imageView.setImageResource(errorImagePlaceholder)
            }
        }).execute(url)
    }

    private class DownloadImageTask(private val responseListener: GeneralResponseListener<Bitmap>)
        : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg params: String?): Bitmap? {
            val url = params[0]
            try {
                val inputStream = URL(url).openStream()
                val bmOptions = BitmapFactory.Options()
                bmOptions.inSampleSize = 2
                return BitmapFactory.decodeStream(inputStream, null, bmOptions)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(loadedBitmap: Bitmap?) {
            if (isCancelled) {
                return
            }

            if (loadedBitmap != null) {
                responseListener.onSuccess(loadedBitmap)
            } else {
                responseListener.onError(Throwable())
            }
        }
    }
}