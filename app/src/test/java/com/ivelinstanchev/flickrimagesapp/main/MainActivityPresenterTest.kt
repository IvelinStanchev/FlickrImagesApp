package com.ivelinstanchev.flickrimagesapp.main

import com.ivelinstanchev.flickrimagesapp.error.GeneralResponseError
import com.ivelinstanchev.flickrimagesapp.flickr.FlickrRepository
import com.ivelinstanchev.flickrimagesapp.listener.GeneralResponseListener
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrAdapterItem
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImage
import com.ivelinstanchev.flickrimagesapp.main.model.FlickrImageLoading
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainActivityPresenterTest {

    companion object {
        private const val SEARCH_QUERY_EMPTY = ""
        private const val SEARCH_QUERY_NOT_EMPTY = "SEARCH_QUERY"
        private const val SEARCH_QUERY_SECOND_NOT_EMPTY = "SEARCH_QUERY_2"
        private const val INITIAL_PAGE = 1
        private const val SECOND_PAGE = 2
        private val FLICKR_IMAGES_LIST_PAGE_1 = mutableListOf(
            FlickrImage("1", "title1", 1, "server1", "secret1"),
            FlickrImage("2", "title2", 2, "server2", "secret2"),
            FlickrImage("3", "title3", 3, "server3", "secret3"))
        private val FLICKR_IMAGES_LIST_PAGE_2 = mutableListOf(
            FlickrImage("4", "title4", 4, "server4", "secret4"),
            FlickrImage("5", "title5", 5, "server5", "secret5"),
            FlickrImage("6", "title6", 6, "server6", "secret6"))
    }

    @Mock
    private lateinit var flickrRepository: FlickrRepository

    @Mock
    private lateinit var mainActivityView: MainActivityContract.View

    private lateinit var mainActivityPresenter: MainActivityContract.Presenter

    @Captor
    private lateinit var flickrImageCallbackCaptor: ArgumentCaptor<GeneralResponseListener<List<FlickrImage>>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        mainActivityPresenter = MainActivityPresenter(mainActivityView, flickrRepository)
    }

    @Test
    fun createPresenter_setsThePresenterToView() {
        verifySetPresenter()
    }

    @Test
    fun submitSearchQuery_NotEmpty() {
        val searchQuery = SEARCH_QUERY_NOT_EMPTY

        mainActivityPresenter.submitSearchQuery(searchQuery)

        verifySubmitSearchQueryNotEmpty(searchQuery)
    }

    @Test
    fun submitSearchQuery_Empty() {
        mainActivityPresenter.submitSearchQuery(SEARCH_QUERY_EMPTY)

        verifyZeroInteractions(flickrRepository)
        verify(mainActivityView, never()).showMainLoading()
        verify(mainActivityView, never()).hideMainLoading()
    }

    @Test
    fun submitSearchQuery_SecondTime_SameQueryString() {
        // verify here because the use of verifyZeroInteractions() method
        verifySetPresenter()

        submitSearchQuery_NotEmpty()

        mainActivityPresenter.submitSearchQuery(SEARCH_QUERY_NOT_EMPTY)

        verifyZeroInteractions(flickrRepository, mainActivityView)
    }

    @Test
    fun submitSearchQuery_SecondTime_DifferentQueryString() {
        submitSearchQuery_NotEmpty()

        val newSearchQuery = SEARCH_QUERY_SECOND_NOT_EMPTY

        mainActivityPresenter.submitSearchQuery(newSearchQuery)

        verifySubmitSearchQueryNotEmpty(newSearchQuery)
    }

    @Test
    fun submitSearchQuery_Error() {
        mainActivityPresenter.submitSearchQuery(SEARCH_QUERY_NOT_EMPTY)

        verifyFetchImages(INITIAL_PAGE, SEARCH_QUERY_NOT_EMPTY)
        val inOrder = inOrder(mainActivityView)
        inOrder.verify(mainActivityView).showMainLoading()

        val error = GeneralResponseError
        flickrImageCallbackCaptor.value.onError(error)

        inOrder.verify(mainActivityView).hideMainLoading()
        inOrder.verify(mainActivityView).onImagesFetchError(error)
    }

    @Test
    fun recyclerScrolledToBottom_NoSubmitQuery() {
        // verify here because the use of verifyZeroInteractions() method
        verifySetPresenter()

        mainActivityPresenter.onRecyclerScrolledToBottom()

        verifyZeroInteractions(flickrRepository, mainActivityView)
    }

    @Test
    fun recyclerScrolledToBottom_FetchSecondPage() {
        val searchQuery = SEARCH_QUERY_NOT_EMPTY
        mainActivityPresenter.submitSearchQuery(searchQuery)
        verifySubmitSearchQueryNotEmpty(searchQuery)

        mainActivityPresenter.onRecyclerScrolledToBottom()

        val inOrder = inOrder(mainActivityView)
        inOrder.verify(mainActivityView).updateImagesRecycler(getPageResultsWithLoading(FLICKR_IMAGES_LIST_PAGE_1))

        verifyFetchImages(SECOND_PAGE, searchQuery)

        flickrImageCallbackCaptor.value.onSuccess(FLICKR_IMAGES_LIST_PAGE_2)

        val allItems = FLICKR_IMAGES_LIST_PAGE_1
        allItems.addAll(FLICKR_IMAGES_LIST_PAGE_2)

        inOrder.verify(mainActivityView).updateImagesRecycler(allItems)
    }

    @Test
    fun recyclerScrolledToBottom_Error() {
        val searchQuery = SEARCH_QUERY_NOT_EMPTY
        mainActivityPresenter.submitSearchQuery(searchQuery)
        verifySubmitSearchQueryNotEmpty(searchQuery)

        mainActivityPresenter.onRecyclerScrolledToBottom()

        val inOrder = inOrder(mainActivityView)
        inOrder.verify(mainActivityView).updateImagesRecycler(getPageResultsWithLoading(FLICKR_IMAGES_LIST_PAGE_1))

        verifyFetchImages(SECOND_PAGE, searchQuery)

        val error = GeneralResponseError
        flickrImageCallbackCaptor.value.onError(error)

        inOrder.verify(mainActivityView).updateImagesRecycler(FLICKR_IMAGES_LIST_PAGE_1)
        inOrder.verify(mainActivityView).onImagesFetchError(error)
    }

    private fun verifySetPresenter() {
        verify(mainActivityView).setPresenter(mainActivityPresenter)
    }

    private fun verifySubmitSearchQueryNotEmpty(searchQuery: String) {
        verifyFetchImages(INITIAL_PAGE, searchQuery)
        val inOrder = inOrder(mainActivityView)
        inOrder.verify(mainActivityView).showMainLoading()

        flickrImageCallbackCaptor.value.onSuccess(FLICKR_IMAGES_LIST_PAGE_1)

        inOrder.verify(mainActivityView).hideMainLoading()
        inOrder.verify(mainActivityView).initImagesRecycler(FLICKR_IMAGES_LIST_PAGE_1)
    }

    private fun verifyFetchImages(page: Int, searchQuery: String) {
        verify(flickrRepository).fetchImages(
            eq(page), eq(searchQuery), capture(flickrImageCallbackCaptor))
    }

    private fun getPageResultsWithLoading(initialData: List<FlickrAdapterItem>): List<FlickrAdapterItem> {
        val newList = initialData.toMutableList()
        newList.add(FlickrImageLoading)

        return newList
    }
}