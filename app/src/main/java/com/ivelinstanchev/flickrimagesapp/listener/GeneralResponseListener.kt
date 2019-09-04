package com.ivelinstanchev.flickrimagesapp.listener

import com.ivelinstanchev.flickrimagesapp.error.ErrorResponse

interface GeneralResponseListener<T> {

    fun onSuccess(response: T)

    fun onError(errorResponse: ErrorResponse)
}