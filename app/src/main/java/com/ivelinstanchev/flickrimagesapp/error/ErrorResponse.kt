package com.ivelinstanchev.flickrimagesapp.error

import android.support.annotation.StringRes
import com.ivelinstanchev.flickrimagesapp.R

sealed class ErrorResponse(@StringRes val messageResId: Int)
object GeneralResponseError : ErrorResponse(R.string.all_response_general_error)