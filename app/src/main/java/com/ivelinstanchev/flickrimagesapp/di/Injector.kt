package com.ivelinstanchev.flickrimagesapp.di

import com.ivelinstanchev.flickrimagesapp.flickr.FlickrApiService
import com.ivelinstanchev.flickrimagesapp.flickr.FlickrRepository

/**
 * Provides instances of classes
 */
object Injector {

    fun provideFlickrRepository(): FlickrRepository {
        return FlickrRepository(FlickrApiService())
    }
}