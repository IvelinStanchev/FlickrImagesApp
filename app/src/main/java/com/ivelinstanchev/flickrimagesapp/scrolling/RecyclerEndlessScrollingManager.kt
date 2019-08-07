package com.ivelinstanchev.flickrimagesapp.scrolling

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Managing scrolling state and checks if fetching new items is needed
 */
class RecyclerEndlessScrollingManager(private val visibleThreshold: Int,
                                      private val fetchFunc: () -> Unit) {

    private var isLoading = true
    private var firstVisibleItemPosition = 0
    private var lastTotalItemsCount = 0
    private var visibleItemsCount = 0
    private var totalItemsCount = 0

    fun reset() {
        firstVisibleItemPosition = 0
        lastTotalItemsCount = 0
        visibleItemsCount = 0
        totalItemsCount = 0
    }

    fun getOnScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    visibleItemsCount = recyclerView.childCount
                    val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
                    totalItemsCount = layoutManager.itemCount
                    firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (isLoading) {
                        if (totalItemsCount > lastTotalItemsCount) {
                            lastTotalItemsCount = totalItemsCount
                            isLoading = false
                        }
                    }
                    if (!isLoading &&
                        totalItemsCount - visibleItemsCount <= firstVisibleItemPosition + visibleThreshold) {
                        isLoading = true
                        fetchFunc.invoke()
                    }
                }
            }
        }
    }
}