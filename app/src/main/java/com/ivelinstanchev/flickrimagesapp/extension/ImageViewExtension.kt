package com.ivelinstanchev.flickrimagesapp.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.ivelinstanchev.flickrimagesapp.R
import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import java.net.URL


fun ImageView.loadWebImage(url: String,
                           @DrawableRes errorImagePlaceholder: Int = R.drawable.ic_image_load_error_placeholder) {
    DownloadImageTask(object : GeneralResponseListener<Bitmap> {
        override fun onSuccess(response: Bitmap) {
            setImageBitmap(response)
        }

        override fun onError(error: Throwable) {
            setImageResource(errorImagePlaceholder)
        }
    }).execute(url)
}

private class DownloadImageTask(private val responseListener: GeneralResponseListener<Bitmap>)
    : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg params: String?): Bitmap? {
        val url = params[0]
        try {
            val inputStream = URL(url).openStream()
            return BitmapFactory.decodeStream(inputStream)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    override fun onPostExecute(loadedBitmap: Bitmap?) {
        if (loadedBitmap != null) {
            responseListener.onSuccess(loadedBitmap)
        } else {
            responseListener.onError(Throwable())
        }
    }
}