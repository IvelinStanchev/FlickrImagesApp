package com.ivelinstanchev.flickrimagesapp.di

import com.ivelinstanchev.flickrimagesapp.flickr.FlickrApiService
import com.ivelinstanchev.flickrimagesapp.flickr.FlickrRepository
import com.ivelinstanchev.flickrimagesapp.util.ImageDownloader

/**
 * Provides instances of classes
 */
object Injector {

    fun provideFlickrRepository(): FlickrRepository {
        return FlickrRepository(FlickrApiService())
    }

    fun provideImageDownloader(): ImageDownloader {
        return ImageDownloader()
    }
}