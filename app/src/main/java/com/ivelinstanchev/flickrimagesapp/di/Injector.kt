package com.ivelinstanchev.flickrimagesapp.di

import com.ivelinstanchev.flickrimagesapp.flickr.FlickrApiService
import com.ivelinstanchev.flickrimagesapp.flickr.FlickrRepository

object Injector {

    fun provideFlickrRepository(): FlickrRepository {
        return FlickrRepository(FlickrApiService())
    }
}