package com.ivelinstanchev.flickrimagesapp.flickr

import android.os.AsyncTask
import com.ivelinstanchev.flickrimagesapp.BuildConfig
import com.ivelinstanchev.flickrimagesapp.flickr.model.RecentImagesResponse
import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImageApiResponse
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

class FlickrApiService {

    companion object {
        private const val RECORDS_PER_PAGE = 30
    }

    private fun buildImagesFetchUrl(page: Int, searchQuery: String): String {
        return "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=${BuildConfig.API_KEY}" +
                "&format=json&nojsoncallback=1&safe_search=1&text=$searchQuery&page=$page&per_page=$RECORDS_PER_PAGE"
    }

    fun fetchImages(page: Int,
                    searchQuery: String,
                    responseListener: GeneralResponseListener<List<FlickrImageApiResponse>>) {
        val url = buildImagesFetchUrl(page, searchQuery)

        DownloadImagesInfoAsyncTask(responseListener).execute(url)
    }

    private class DownloadImagesInfoAsyncTask(
        private val responseListener: GeneralResponseListener<List<FlickrImageApiResponse>>)
        : AsyncTask<String, Void, List<FlickrImageApiResponse>?>() {

        override fun doInBackground(vararg params: String?): List<FlickrImageApiResponse>? {
            val urlPath = params[0]

            var urlConnection: HttpURLConnection? = null
            try {
                val url = URL(urlPath)
                urlConnection = url.openConnection() as HttpURLConnection

                val response = urlConnection.inputStream.bufferedReader().use(BufferedReader::readText)
                return parseJsonResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()
            }

            return null
        }

        override fun onPostExecute(result: List<FlickrImageApiResponse>?) {
            super.onPostExecute(result)
            if (result != null) {
                responseListener.onSuccess(result)
            } else {
                responseListener.onError(Throwable())
            }
        }

        private fun parseJsonResponse(rawJson: String?): List<FlickrImageApiResponse>? {
            if (rawJson.isNullOrEmpty()) {
                return null
            }

            return RecentImagesResponse(rawJson).photos
        }
    }
}