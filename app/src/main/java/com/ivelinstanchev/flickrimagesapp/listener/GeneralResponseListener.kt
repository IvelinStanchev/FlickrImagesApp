package com.ivelinstanchev.flickrimagesapp.listener

interface GeneralResponseListener<T> {

    fun onSuccess(response: T)

    fun onError(error: Throwable)
}