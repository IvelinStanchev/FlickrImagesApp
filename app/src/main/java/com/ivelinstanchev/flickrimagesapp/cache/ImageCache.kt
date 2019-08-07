package com.ivelinstanchev.flickrimagesapp.cache

import android.graphics.Bitmap
import android.util.LruCache

/**
 * Memory cache for bitmaps. URLs will be mapped to downloaded bitmaps and bitmaps can be retrieved from specified URL.
 * The cache has max memory limitation which is 1/8th of the available memory.
 */
object ImageCache {

    private var memoryCache: LruCache<String, Bitmap>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {

            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.byteCount / 1024
            }
        }
    }

    fun getBitmap(key: String): Bitmap? {
        return memoryCache.get(key)
    }

    fun putBitmap(key: String, bitmap: Bitmap) {
        memoryCache.put(key, bitmap)
    }
}